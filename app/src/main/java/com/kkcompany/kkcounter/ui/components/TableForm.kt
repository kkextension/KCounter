package com.kkcompany.kkcounter.ui.components

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kkcompany.kkcounter.R
import com.kkcompany.kkcounter.ui.theme.ButtonNavy
import com.kkcompany.kkcounter.viewmodel.TableViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RowScope.TableForm(
    context: Context,
    viewModel: TableViewModel,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope
) {

    Column(
        modifier = Modifier
            .weight(3f)
            .fillMaxHeight()
    ) {
        Text(
            text = stringResource(id = R.string.add_or_edit_table_name),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = viewModel.uiState.name,
            onValueChange = { viewModel.updateUiState(name = it) },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.DarkGray,
                unfocusedBorderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(id = R.string.add_or_edit_table_description),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = viewModel.uiState.description,
            onValueChange = { viewModel.updateUiState(description = it) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.DarkGray,
                unfocusedBorderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        RoundedButton(
            modifier = Modifier.padding(horizontal = 4.dp),
            text = stringResource(id = R.string.button_save),
            color = ButtonNavy,
            height = 48.dp,
            onClick = {
                if (viewModel.uiState.name.isBlank()) {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = context.getString(R.string.snackBar_table_name_empty)
                        )
                    }
                } else {
                    viewModel.addTable()
                    viewModel.finishAddingTable()
                }
            }
        )
    }
}
