@file:OptIn(ExperimentalMaterialApi::class)

package app.khipu.android.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.khipu.android.ui.theme.PanelBackground
import app.khipu.android.ui.theme.PlaceholderColor
import app.khipu.android.ui.theme.TextColor
import app.khipu.android.ui.theme.screenHeader

@Composable
fun StandardAppButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    content: (@Composable () -> Unit)? = null,
    onClick: () -> Unit,
) {
    if ((text == null && content == null) || (text != null && content != null))
        throw IllegalArgumentException("Cannot create button: either text and button's content == null or != null at once!")

    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = PanelBackground,
            contentColor = Color.White
        ),
        elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
        border = BorderStroke(3.dp, Color.White),
        contentPadding = if (text != null) PaddingValues(8.dp) else PaddingValues(5.dp)
    ) {
        if (text != null) {
            Text(text = text, style = screenHeader)
        } else {
            content!!.invoke()
        }
    }
}

@Composable
fun StandardAppOutlinedTextField(
    modifier: Modifier = Modifier,
    hint: String = "",
    text: String,
    onTextChange: (String) -> Unit,
    singleLine: Boolean = true,
) {
    BasicTextField(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .border(width = 1.dp, color = PanelBackground, RoundedCornerShape(50)),
        value = text,
        onValueChange = onTextChange,
        textStyle = screenHeader.copy(textAlign = TextAlign.Start),
        singleLine = singleLine,
    ) {
        TextFieldDefaults.TextFieldDecorationBox(
            value = text,
            innerTextField = {
                if (text.isBlank()) {
                    //todo center horizontally later
                    Text(
                        text = hint,
                        color = PlaceholderColor,
                        style = screenHeader,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = if (singleLine) 1 else Int.MAX_VALUE,
                    )
                }
                it()
            },
            enabled = true,
            singleLine = singleLine,
            visualTransformation = VisualTransformation.None,
            interactionSource = remember { MutableInteractionSource() },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = TextColor,
            ),
            contentPadding = PaddingValues(horizontal = 15.dp, vertical = 10.dp),
        )
    }
}

@Preview
@Composable
fun ButtonsPreview() {
    Column {
        StandardAppButton(text = "войти", modifier = Modifier.width(221.dp)) {}
        StandardAppButton(content = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = Icons.AutoMirrored.Filled.KeyboardArrowLeft.name
            )
        }, modifier = Modifier.size(36.dp)) {}
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldsPreview() {
    Column {
        StandardAppOutlinedTextField(text = "Text!!!", onTextChange = {}, hint = "имя пользователя")
        StandardAppOutlinedTextField(
            text = "It's very very very very very very very very looooooooong text!!!!!!!!!!!!!!",
            onTextChange = {}, hint = "имя пользователя"
        )
        StandardAppOutlinedTextField(text = "", onTextChange = {}, hint = "имя пользователя")
        StandardAppOutlinedTextField(text = "", onTextChange = {}, hint = "пароль")
        StandardAppOutlinedTextField(
            text = "",
            onTextChange = {},
            hint = "It's very very very very very very very very looooooooong text!!!!!!!!!!!!!!"
        )
    }
}
