package com.example.employeetaskreg.presentation.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MailOutline
import androidx.compose.material.icons.rounded.ModeEditOutline
import androidx.compose.material.icons.rounded.PeopleOutline
import androidx.compose.material.icons.rounded.PersonOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.employeetaskreg.R
import com.example.employeetaskreg.presentation.ui.components.BottomNavigation
import com.example.employeetaskreg.presentation.viewmodel.MainViewModel

@Composable
fun BottomNavigationBar(navController: NavHostController, viewModel: MainViewModel){
    val role = viewModel.userRole.observeAsState().value!!

    val directorItems = listOf(
        BottomNavigation(
            route = "emp_list",
            icon = Icons.Rounded.PeopleOutline,
            title = stringResource(id = R.string.employees)
        ),
        BottomNavigation(
            route = "tasks",
            icon = Icons.Rounded.ModeEditOutline,
            title = stringResource(id = R.string.tasks)
        ),
        BottomNavigation(
            route = "resp",
            icon = Icons.Rounded.MailOutline,
            title = stringResource(id = R.string.responds)
        ),
        BottomNavigation(
            route = "profile",
            icon = Icons.Rounded.PersonOutline,
            title = stringResource(id = R.string.profile)
        ),
    )
    val empItems = listOf(
        BottomNavigation(
            route = "tasks",
            icon = Icons.Rounded.ModeEditOutline,
            title = stringResource(id = R.string.tasks)
        ),
        BottomNavigation(
            route = "resp",
            icon = Icons.Rounded.MailOutline,
            title = stringResource(id = R.string.responds)
        ),
        BottomNavigation(
            route = "profile",
            icon = Icons.Rounded.PersonOutline,
            title = stringResource(id = R.string.profile)
        ),
    )
    NavigationBar(containerColor = (MaterialTheme.colorScheme.secondaryContainer)){

        //Отслеживание текушего маршрута
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        var items:List<BottomNavigation> = listOf()
        when(role){
            "1" -> items = directorItems
            "2" -> items = empItems
        }
        Row()
        {
            items.forEach { item->
                NavigationBarItem(selected = currentRoute == item.route, modifier = Modifier.semantics
                { contentDescription = item.route },
                    onClick = {
                        navController.popBackStack()
                        navController.navigate(item.route)
                    },
                    icon = {
                        Icon(imageVector = item.icon,
                            contentDescription =item.route,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer)
                    },
                    label = {
                        Text(text = item.title, color = MaterialTheme.colorScheme.onSecondaryContainer)
                    })
            }
        }
    }


}