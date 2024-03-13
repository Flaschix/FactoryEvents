package com.example.factoryevents.presentation.ReportOrder

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.factoryevents.R
import com.example.factoryevents.presentation.FactoryEventApplication
import java.time.LocalTime
import java.time.format.DateTimeFormatter
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
                Text(text = "Report")
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

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .background(colorResource(id = R.color.firstColor))
        ) {

            //что произошло?
            TextFieldWithHelperMessage(
                stringResource(id = R.string.what_happened),
                stringResource(id = R.string.describe_detected_deviation)
            ){
                viewModel.updateWhatHappened(it)
            }

            //Эта проблема уже случалась ранее?
            Spacer(modifier = Modifier.height(10.dp))

            RecurrentIssueButtons(){
                viewModel.updateIsItARecurrentIssue(it)
            }

            //Почему это проблема?
            Spacer(modifier = Modifier.height(10.dp))

            TextFieldWithHelperMessage(
                stringResource(id = R.string.why_is_it_problem),
                stringResource(id = R.string.describe_what_standard_violated)
            ){
                viewModel.updateWhyIsItProblem(it)
            }

            Spacer(modifier = Modifier.height(10.dp))

            PhotoSelectorView(maxSelectionCount = 3){
                viewModel.updateImage(it)
            }

            Spacer(modifier = Modifier.height(10.dp))

            //Время
            TimeField(
                stringResource(id = R.string.time)
            ){
                viewModel.updateTime(it)
            }

            Spacer(modifier = Modifier.height(10.dp))

            //Дата
            DateField(
                stringResource(id = R.string.date)
            ){
                viewModel.updateDate(it)
            }

            //Кто обнаружил?
            Spacer(modifier = Modifier.height(10.dp))

            TextFieldWithHelperMessage(
                stringResource(id = R.string.who_detected_it),
                stringResource(id = R.string.write_your_name_and_position)
            ){
                viewModel.updateWhoDetectedIt(it)
            }

            //Как это было обнаружено?
            Spacer(modifier = Modifier.height(10.dp))

            TextFieldWithHelperMessage(
                stringResource(id = R.string.how_it_was_detected),
                stringResource(id = R.string.visually_during_test_audit)
            ){
                viewModel.updateHowItWasDetected(it)
            }

            //Сколько отклонений обнаружено?
            Spacer(modifier = Modifier.height(10.dp))

            NumberOutlinedTextFieldSample(stringResource(id = R.string.how_many_deviations_detected)){
                viewModel.updateDetectionCount(it)
            }

            //Укажите своего руководителя либо руководителя департамента, где обнаружено нарушение.
//            TODO()
            Spacer(modifier = Modifier.height(10.dp))

            Demo_SearchableExposedDropdownMenuBox(
                stringResource(id = R.string.manager),
                stringResource(id = R.string.indicate_your_manager)
            ){
                viewModel.updateIndicateYourManager(it)
            }

            //Кнопка
            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    viewModel.createOrder()

                    //-----------------------------------------------------------------------------------/
                    onBackPressedListener() //-------------------------Добавить дожидания отправки-------/
                    //-----------------------------------------------------------------------------------/
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.secondColor)),
                border = BorderStroke(1.dp, color = colorResource(id = R.color.white))
            ) {
                Text(
                    text = stringResource(id = R.string.report_violation),
                    color = colorResource(id = R.color.white)
                )
            }

            Spacer(modifier = Modifier.height(100.dp))

        }

    }
}

@Composable
private fun TextFieldWithHelperMessage(descript: String, helpMsg: String, update: (data: String) -> Unit) {
    var text by rememberSaveable { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(
                text = descript,
                color = colorResource(id = R.color.white)
            ) },
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = colorResource(id = R.color.secondColor),
                unfocusedBorderColor = colorResource(id = R.color.white),
                focusedBorderColor = colorResource(id = R.color.white),
                cursorColor = colorResource(id = R.color.white),
                focusedLabelColor = colorResource(id = R.color.white),
                textColor = colorResource(id = R.color.white)
            ),
        )
        update(text)
        Text(
            text = helpMsg,
            color = colorResource(id = R.color.white),
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
private fun NumberOutlinedTextFieldSample(descript: String, update: (data: String) -> Unit) {
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
        label = { Text(
            text = descript,
            color = colorResource(id = R.color.white)
        ) },
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = colorResource(id = R.color.secondColor),
            unfocusedBorderColor = colorResource(id = R.color.white),
            focusedBorderColor = colorResource(id = R.color.white),
            cursorColor = colorResource(id = R.color.white),
            focusedLabelColor = colorResource(id = R.color.white),
            textColor = colorResource(id = R.color.white)
        )
    )

    update(text)
}

@Composable
private fun RecurrentIssueButtons(update: (data: String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, color = colorResource(id = R.color.white), RoundedCornerShape(10.dp))
            .background(colorResource(id = R.color.secondColor))
    ){
        Text(
            text = stringResource(id = R.string.is_it_a_recurrent_issue),
            style = MaterialTheme.typography.body1.merge(),
            modifier = Modifier.padding(start = 8.dp, top = 6.dp),
            fontSize = 16.sp,
            color = colorResource(id = R.color.white)
        )

        val radioOptions = listOf("Да", "Нет", "Не могу ответить")
        val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
        // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
        Column(Modifier.selectableGroup()) {

            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .padding(start = 8.dp)
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = { onOptionSelected(text) },
                            role = Role.RadioButton
                        )
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorResource(id = R.color.white),
                            unselectedColor = colorResource(id = R.color.white)
                        ),
                        onClick = null // null recommended for accessibility with screenreaders
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.body1.merge(),
                        modifier = Modifier.padding(start = 16.dp),
                        color = colorResource(id = R.color.white)
                    )
                    if(text == selectedOption) update(text)

                }
            }
        }
    }
}

@Composable
private fun TimeField(helpMsg: String, update: (data: String) -> Unit){
    // Fetching local context
    val mContext = LocalContext.current

    // Declaring and initializing a calendar
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    // Value for storing time as a string
    val mTime = remember { mutableStateOf(LocalTime.now()) }

    // Creating a TimePicker dialod
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            mTime.value = LocalTime.of(mHour, mMinute)
        }, mHour, mMinute, false
    )

    val timeFormat = DateTimeFormatter.ofPattern("HH:mm")

    Column {
        OutlinedTextField(
            value = mTime.value.format(timeFormat),
            onValueChange = { mTimePickerDialog.show() },
            label = {  },
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .onFocusChanged {
                    if (it.isFocused) {
                        mTimePickerDialog.show()
                    } else {
                        // not focused
                    }
                },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = colorResource(id = R.color.secondColor),
                unfocusedBorderColor = colorResource(id = R.color.white),
                focusedBorderColor = colorResource(id = R.color.white),
                cursorColor = colorResource(id = R.color.white),
                focusedLabelColor = colorResource(id = R.color.white),
                textColor = colorResource(id = R.color.white)
            )
        )

        update(mTime.value.format(timeFormat))

        Text(
            text = helpMsg,
            color = colorResource(id = R.color.white),
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(start = 16.dp)
        )
    }

}

@Composable
fun DateField(helpMsg: String, update: (data: String) -> Unit){

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
    val mDate = remember { mutableStateOf("$mDay/${mMonth+1}/$mYear") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
        }, mYear, mMonth, mDay
    )

    Column {
        OutlinedTextField(
            value = mDate.value,
            onValueChange = { mDatePickerDialog.show() },
            label = {  },
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .onFocusChanged {
                    if (it.isFocused) {
                        mDatePickerDialog.show()
                    } else {
                        // not focused
                    }
                },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = colorResource(id = R.color.secondColor),
                unfocusedBorderColor = colorResource(id = R.color.white),
                focusedBorderColor = colorResource(id = R.color.white),
                cursorColor = colorResource(id = R.color.white),
                focusedLabelColor = colorResource(id = R.color.white),
                textColor = colorResource(id = R.color.white)
            )
        )
        update(mDate.value)
        Text(
            text = helpMsg,
            color = colorResource(id = R.color.white),
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun PhotoSelectorView(maxSelectionCount: Int = 1, update: (data: Uri) -> Unit) {
    var selectedImages by remember {
        mutableStateOf<List<Uri?>>(emptyList())
    }

    val buttonText = "Select a photo"

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImages = listOf(uri) }
    )

    fun launchPhotoPicker() {
        singlePhotoPickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (selectedImages.isNotEmpty()) {
            update(selectedImages[0]!!)

            AsyncImage(
                model = selectedImages[0],
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                    .background(colorResource(id = R.color.secondColor), RoundedCornerShape(10.dp))
                    .border(1.dp, colorResource(id = R.color.white), RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Fit
            )
        }
//        ImageLayoutView(selectedImages = selectedImages)

        Button(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            onClick = { launchPhotoPicker() },
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.secondColor)),
            border = BorderStroke(1.dp, color = colorResource(id = R.color.white))
        ) {
            Text(
                text = buttonText,
                 color = colorResource(id = R.color.white)
            )
        }
    }
}


@Composable
fun ImageLayoutView(selectedImages: List<Uri?>) {
    LazyRow (
        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
    ) {
        items(selectedImages) { uri ->
            AsyncImage(
                model = uri,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
        }
    }
}




@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Demo_SearchableExposedDropdownMenuBox(title: String, helpMsg: String, update: (data: String) -> Unit) {
    val context = LocalContext.current
    val people = arrayOf(
        Pair("anastasia oleynikova", "smm.20@uni-dubna.ru"),
        Pair("vladimir lyalin", "smm.20@uni-dubna.ru"),
        Pair("evgeniy pankov", "smm.20@uni-dubna.ru"),
        Pair("ilya ryabinin", "smm.20@uni-dubna.ru"),
        Pair("sergey ashikhmin", "smm.20@uni-dubna.ru"),
        Pair("evgeniy saleev", "smm.20@uni-dubna.ru"),
        Pair("vadim sorokin", "smm.20@uni-dubna.ru"),
        Pair("konstantin putilkin", "smm.20@uni-dubna.ru"),
        Pair("vyacheslav pashkin", "smm.20@uni-dubna.ru"),
        Pair("ilya ryabinin", "smm.20@uni-dubna.ru"),
        Pair("andrey korsunov", "smm.20@uni-dubna.ru"),
        Pair("maksim gvozdev", "smm.20@uni-dubna.ru"),

    )
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    Column(

    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = { selectedText = it },
                label = { Text(
                    text = title,
                    color = colorResource(id = R.color.white)
                ) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = colorResource(id = R.color.secondColor),
                    unfocusedBorderColor = colorResource(id = R.color.white),
                    focusedBorderColor = colorResource(id = R.color.white),
                    cursorColor = colorResource(id = R.color.white),
                    focusedLabelColor = colorResource(id = R.color.white),
                    textColor = colorResource(id = R.color.white),
                    trailingIconColor = colorResource(id = R.color.white)
                )
            )

            val filteredOptions =
                people.filter { it.first.contains(selectedText, ignoreCase = true) }
            if (filteredOptions.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        // We shouldn't hide the menu when the user enters/removes any character
                    }
                ) {
                    filteredOptions.forEach { item ->
                        DropdownMenuItem(
                            modifier = Modifier.background(colorResource(id = R.color.secondColor)),
                            onClick = {
                                selectedText = item.first
                                expanded = false
                                Toast.makeText(context, item.first, Toast.LENGTH_SHORT).show()
                                update(item.second)
                            },
                        ){
                            Text(
                                text = item.first,
                                color = colorResource(id = R.color.white),
                            )
                        }
                    }
                }
            }
        }
        Text(
            text = helpMsg,
            color = colorResource(id = R.color.white),
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(start = 16.dp)
        )
    }

}





