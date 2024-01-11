package com.example.factoryevents.presentation.FireOrder

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.factoryevents.R
import com.example.factoryevents.domain.entity.Order
import com.example.factoryevents.presentation.FactoryEventApplication
import java.util.Calendar
import java.util.Date

@Composable
fun ReportScreen(
    onBackPressedListener: () -> Unit
){

    val component = (LocalContext.current.applicationContext as FactoryEventApplication)
        .component
        .getReportOrderComponentFactory()
        .create()

    val viewModel: ReportScreenViewModel = viewModel(factory = component.getViewModelFactory())

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "FireOrder")
            },
                navigationIcon = {
                    IconButton(onClick = { onBackPressedListener() }) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ){
        it;

        Column {

            //что произошло?
            TextFieldWithHelperMessage(
                stringResource(id = R.string.what_happened),
                stringResource(id = R.string.describe_detected_deviation)
            )

            //Эта проблема уже случалась ранее?
            Spacer(modifier = Modifier.height(10.dp))

            RecurrentIssueButtons()

            //Почему это проблема?
            Spacer(modifier = Modifier.height(10.dp))

            TextFieldWithHelperMessage(
                stringResource(id = R.string.why_is_it_problem),
                stringResource(id = R.string.describe_what_standard_violated)
            )

            //Когда это произошло?
            //Время
            //Дата


            //Кто обнаружил?
            Spacer(modifier = Modifier.height(10.dp))

            TextFieldWithHelperMessage(
                stringResource(id = R.string.who_detected_it),
                stringResource(id = R.string.write_your_name_and_position)
            )

            //Как это было обнаружено?
            Spacer(modifier = Modifier.height(10.dp))

            TextFieldWithHelperMessage(
                stringResource(id = R.string.how_it_was_detected),
                stringResource(id = R.string.visually_during_test_audit)
            )

            //Сколько отклонений обнаружено?
            Spacer(modifier = Modifier.height(10.dp))

            NumberOutlinedTextFieldSample(stringResource(id = R.string.how_many_deviations_detected))

            //Укажите своего руководителя либо руководителя департамента, где обнаружено нарушение.


            //Кнопка
            Button(
                onClick = {
                    viewModel.createOrder(parseToOrder("","",""))

                    //-----------------------------------------------------------------------------------/
                    onBackPressedListener() //-------------------------Добавить дожидания отправки-------/
                    //-----------------------------------------------------------------------------------/
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                Text(text = stringResource(id = R.string.report_violation))
            }

        }

    }
}

@Composable
private fun TextFieldWithHelperMessage(descript: String, helpMsg: String) {
    var text by rememberSaveable { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(descript) },
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
        )
        Text(
            text = helpMsg,
            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
private fun NumberOutlinedTextFieldSample(descript: String) {
    var text by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = true,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        value = text,
        onValueChange = { text = if(it.toIntOrNull() != null) it else text },
        label = { Text(descript) },
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
}

@Composable
private fun RecurrentIssueButtons() {
    Text(
        text = stringResource(id = R.string.is_it_a_recurrent_issue),
        style = MaterialTheme.typography.body1.merge(),
    )

    val radioOptions = listOf("Да", "Нет", "Не могу ответить")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
    Column(Modifier.selectableGroup()) {

        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = null // null recommended for accessibility with screenreaders
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.body1.merge(),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun ReportViolation(viewModel: ReportScreenViewModel, order: Order){

}

private fun parseToOrder(p1: String, p2: String, p3: String): Order{
    return Order()
}


@Composable
fun MainContent() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("GFG | Date Picker", color = Color.White) }, backgroundColor = Color(0xff0f9d58)) },
        content = {
            it;
            MyContent()
        }
    )
}

// Creating a composable function to
// create two Images and a spacer between them
// Calling this function as content
// in the above function
@Composable
fun MyContent(){

    // Fetching the Local Context
    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val mDate = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
        }, mYear, mMonth, mDay
    )

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        // Creating a button that on
        // click displays/shows the DatePickerDialog
        Button(onClick = {
            mDatePickerDialog.show()
        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58)) ) {
            Text(text = "Open Date Picker", color = Color.White)
        }

        // Adding a space of 100dp height
        Spacer(modifier = Modifier.size(100.dp))

        // Displaying the mDate value in the Text
        Text(text = "Selected Date: ${mDate.value}", fontSize = 30.sp, textAlign = TextAlign.Center)
    }
}


