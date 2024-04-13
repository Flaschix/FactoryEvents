package com.example.factoryevents.presentation.OJT

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.factoryevents.R
import com.example.factoryevents.domain.entity.OJT
import com.example.factoryevents.presentation.HSE.SortTypeOwn
import com.example.factoryevents.presentation.ViewModelFactory

@Composable
fun OjtScreen(
    viewModelFactory: ViewModelFactory,
    onOjtClickListener: (ojt: OJT) -> Unit
){
    val viewModel: OjtScreenViewModel = viewModel(factory = viewModelFactory)
    val screenState = viewModel.screenState.collectAsState(OjtScreenState.Initial)


    when(val currentState = screenState.value){
        is OjtScreenState.OJT_List -> {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.firstColor))
            ){
                ShowOjtList(
                    list = currentState.list,
                    viewModel = viewModel,
                    onClickListener = onOjtClickListener
                )
            }
        }
        is OjtScreenState.Initial -> {}
        is OjtScreenState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.firstColor)),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(
                    color = colorResource(id = R.color.white)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ShowOjtList(
    list: List<OJT>,
    viewModel: OjtScreenViewModel,
    onClickListener: (ojt: OJT) -> Unit
){
    var currentSortTypeIncident by remember { mutableStateOf<SortTypeIncident>(SortTypeIncident.ALL) }
    var currentSortTypeOwn by remember { mutableStateOf<SortTypeOwn>(SortTypeOwn.All) }
    var currentPlace by remember { mutableStateOf<String>("Все") }

    val sortedListType = when (currentSortTypeIncident){
        SortTypeIncident.ALL -> list
        SortTypeIncident.COVID -> list.filter { it.type == "COVID19" }
        SortTypeIncident.SAF -> list.filter { it.type == "SAF" }
        SortTypeIncident.SGR -> list.filter { it.type == "SGR" }
    }

    val sortedListOwn = when (currentSortTypeOwn){
        SortTypeOwn.All -> sortedListType
        SortTypeOwn.Mine -> sortedListType.filter { it.areResponsible == "Сигалов М М" || it.pilot == "Сигалов М М"}
    }

    val sortedList = if(currentPlace == "Все") sortedListOwn else { sortedListOwn.filter { it.place == currentPlace } }

    LazyColumn(
        contentPadding = PaddingValues(top = 10.dp, start = 6.dp, end = 6.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.background(colorResource(id = R.color.firstColor))
    ){
        item {
            Column {
                SortDisplayType(
                    sortTypeIncident = currentSortTypeIncident,
                    onNext = { currentSortTypeIncident = currentSortTypeIncident.getNext() },
                    onPrevious = { currentSortTypeIncident = currentSortTypeIncident.getPrevious() }
                )

                Spacer(modifier = Modifier.padding(top = 6.dp))
                
                SortDisplayOwn(
                    sortTypeOwn = currentSortTypeOwn,
                    onNext = { currentSortTypeOwn = currentSortTypeOwn.getNext() },
                    onPrevious = { currentSortTypeOwn = currentSortTypeOwn.getNext() }
                )

                Demo_SearchableExposedDropdownMenuBox(
                    title = currentPlace,
                    helpMsg = stringResource(
                    id = R.string.incident_place
                )){
                    currentPlace = it
                }
            }

        }
        items(sortedList, key = {it.id}){ ojt ->
//            Log.d("OJT_TEST", "${ojt.img}: ${ojt.status}, id: ${ojt.id}")
            OjtItem(
                ojt = ojt,
                onClickListener = {
                    onClickListener(ojt)
                },
                modifier = Modifier.animateItemPlacement()
            )
        }
    }
}

@Composable
private fun OjtItem(
    ojt: OJT,
    onClickListener: (ojt: OJT) -> Unit,
    modifier: Modifier
){

    Card(
        modifier = modifier
            .clip(RectangleShape)
            .padding(4.dp)
            .clickable(
                onClick = {
                    onClickListener(ojt)
                }
            )
    ) {
        Header(ojt)
    }
}

@Composable
fun Header(ojt: OJT){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (ojt.status) colorResource(id = R.color.teal_200) else colorResource(R.color.pink))
            .padding(horizontal = 3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier
            .weight(2f)
        ) {
            Text(
                text = "Тип: ${ojt.type}",
                color = colorResource(id = R.color.black)
            )

//            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "Неделя: ${ojt.week}",
                color = colorResource(id = R.color.black)
            )
            Spacer(modifier = Modifier.height(3.dp))

                IconNearText(
                iconId = R.drawable.pilot_24,
                text = ojt.pilot,
            )

            Spacer(modifier = Modifier.height(3.dp))

            IconNearText(
                iconId = R.drawable.place_24,
                text = ojt.place,
            )

            Spacer(modifier = Modifier.height(3.dp))

            IconNearText(
                iconId = R.drawable.responsible_24,
                text = ojt.areResponsible,
            )

        }

        Column(
            modifier = Modifier
                .weight(1f),
//            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = R.drawable.timer_24),
                tint = colorResource(id = R.color.black),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = ojt.dueDate,
                color = colorResource(id = R.color.black)
            )
        }

        AsyncImage(
            model = "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhC5mIuy2KaMll2PVI4iE_18w4c7zDCcSi0PzWijCWeakJEJRGhAnIaSkYGLK7dEYlTwYnqTzwOJJcRxBrNFrZMsRovChY8CVpVIXH5pNPPj5wo1kPfGeth4z690xixWqd69vceT1yMaxGB4nDNXUnQ-kuJm3yHgqJreLneAV0nWp4lsF-BPFX0CgM8Tw/w1200-h630-p-k-no-nu/card%20view.jpg",
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .clip(CircleShape),
            contentDescription = ""
        )
    }
}

@Composable
fun IconNearText(
    iconId: Int,
    text: String,
    itemClickListener: (() -> Unit)? = null,
    tint: Color = colorResource(id = R.color.black),
    textColor: Color = colorResource(id = R.color.black)
) {
    val modifier = if(itemClickListener == null) Modifier
    else Modifier.clickable { itemClickListener() }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = tint
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = text,

            color = textColor
        )
    }
}


@Composable
private fun SortDisplayType(
    sortTypeIncident: SortTypeIncident,
    onNext : () -> Unit,
    onPrevious: () -> Unit
){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .background(colorResource(id = R.color.secondColor), RoundedCornerShape(10.dp))
                .border(1.dp, colorResource(id = R.color.white), RoundedCornerShape(10.dp))
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(10.dp))
                .clickable { onPrevious() }
        ){
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "Back",
                tint = colorResource(id = R.color.white),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Text(
            text = "Тип: ${sortTypeIncident.name}",
            color = colorResource(id = R.color.white),
            modifier = Modifier
                .background(colorResource(id = R.color.secondColor), RoundedCornerShape(10.dp))
                .border(1.dp, colorResource(id = R.color.white), RoundedCornerShape(10.dp))
                .padding(horizontal = 20.dp, vertical = 12.dp)
        )

        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .background(colorResource(id = R.color.secondColor), RoundedCornerShape(10.dp))
                .border(1.dp, colorResource(id = R.color.white), RoundedCornerShape(10.dp))
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(10.dp))
                .clickable { onNext() }
        ){
            Icon(
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = "Forward",
                tint = colorResource(id = R.color.white),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun SortDisplayOwn(
    sortTypeOwn: SortTypeOwn,
    onNext : () -> Unit,
    onPrevious: () -> Unit
){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .background(colorResource(id = R.color.secondColor), RoundedCornerShape(10.dp))
                .border(1.dp, colorResource(id = R.color.white), RoundedCornerShape(10.dp))
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(10.dp))
                .clickable { onPrevious() }
        ){
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "Back",
                tint = colorResource(id = R.color.white),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Text(
            text = "Список: ${sortTypeOwn.name}",
            color = colorResource(id = R.color.white),
            modifier = Modifier
                .background(colorResource(id = R.color.secondColor), RoundedCornerShape(10.dp))
                .border(1.dp, colorResource(id = R.color.white), RoundedCornerShape(10.dp))
                .padding(horizontal = 20.dp, vertical = 12.dp)
        )

        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .background(colorResource(id = R.color.secondColor), RoundedCornerShape(10.dp))
                .border(1.dp, colorResource(id = R.color.white), RoundedCornerShape(10.dp))
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(10.dp))
                .clickable { onNext() }
        ){
            Icon(
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = "Forward",
                tint = colorResource(id = R.color.white),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Demo_SearchableExposedDropdownMenuBox(title: String, helpMsg: String, update: (data: String) -> Unit) {
    val context = LocalContext.current
    val place = arrayOf(
        "Все",
        "PTS PLANT",
        "THS TPT",
        "THS PLANT",
        "PTS PES",
        "THS SC",
        "PTS PTR",
        "THS MNT",
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
                    text = helpMsg,
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
//            Text(
//                text = helpMsg,
//                color = colorResource(id = R.color.white),
//                style = MaterialTheme.typography.caption,
//                modifier = Modifier.padding(start = 16.dp)
//            )

            val filteredOptions =
                place.filter { it.contains(selectedText, ignoreCase = true) }
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
                                selectedText = item
                                expanded = false
                                Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                                update(item)
                            },
                        ){
                            Text(
                                text = item,
                                color = colorResource(id = R.color.white),
                            )
                        }
                    }
                }
            }
        }
    }
}