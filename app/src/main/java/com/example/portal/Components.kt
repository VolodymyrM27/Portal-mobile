package com.example.portal

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.portal.ui.theme.BrightGreen

@Composable
fun TextEditField(
    icon: @Composable () -> Unit,
    @StringRes labelId: Int,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next
    ),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        leadingIcon = icon,
        shape = RoundedCornerShape(50.dp),
        label = { Text(stringResource(labelId), style = MaterialTheme.typography.body2) },
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.primary,
            focusedLabelColor = MaterialTheme.colors.onPrimary,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier.shadow(elevation = 2.dp, RoundedCornerShape(50.dp))
    )
}

@Composable
fun StyledButton(onClick: () -> Unit, icon: @Composable () -> Unit, @StringRes textId: Int) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50.dp),
        elevation = null,
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .shadow(elevation = 2.dp, RoundedCornerShape(50.dp))
    ) {
        icon()
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            stringResource(textId),
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CustomCircularProgressIndicator() {
    Box(
        Modifier
            .background(MaterialTheme.colors.background.copy(alpha = 0.6f))
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(size = 64.dp),
            color = MaterialTheme.colors.secondary,
            strokeWidth = 8.dp
        )
    }
}

@Composable
fun GreenBox(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 15.dp),
        shape = RoundedCornerShape(15.dp),
        BrightGreen
    ) {
        content()
    }
}

@Composable
fun Header(text: String) {
    GreenBox(content = {
        Box(
            modifier = Modifier
                .size(0.dp, 50.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        )
        {
            Text(text = text, style = MaterialTheme.typography.h2)
        }
    })
}
