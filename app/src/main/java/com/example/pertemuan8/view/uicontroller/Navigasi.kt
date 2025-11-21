package com.example.pertemuan8.view.uicontroller


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pertemuan8.model.DataJk
import com.example.pertemuan8.model.DataJk.jenisK
import com.example.pertemuan8.view.FormIsian
import com.example.pertemuan8.view.TampilData
import com.example.pertemuan8.viewmodel.SiswaViewModel

enum class Navigasi {
    Formulir,
    Detail
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SiswaApp(
    // edit 1 : parameter viewModel
    modifier: Modifier,
    viewModel: SiswaViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
){
    Scaffold { isiRuang->
        // edit 2 : tambahkan variable uiState
        val uiState = viewModel.statusUI.collectAsState()
        NavHost(
            navController = navController,
            startDestination = Navigasi.Formulir.name,

            modifier = Modifier.padding(isiRuang)){
            composable(route = Navigasi.Formulir.name){
                //edit 3 : tambahkan variable konteks
                val konteks = LocalContext.current
                FormIsian (
                    // edit 4 : parameter pilihanJK dan onSubmitButtonCliked
                    pilihanJk = jenisK,
                    OnSubmitButtonClicked = {
                        viewModel.setSiswa(it)
                        navController.navigate(Navigasi.Detail.name)
                    }
                )
            }
            composable(route = Navigasi.Detail.name){
                TampilData (
                    // edit 5 : parameter statusUiSiswa
                    statusUISiswa = uiState.value,
                    onBackButtonCliked = {
                        cancelAndBackToFormulir(navController)
                    }
                )
            }
        }
    }
}

private fun cancelAndBackToFormulir(
    navController: NavHostController
) {
    navController.popBackStack(Navigasi.Formulir.name, inclusive = false)
}