package common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OutlinedContainer(
    title: @Composable() () -> Unit,
    content: @Composable() () -> Unit,
    modifier: Modifier = Modifier
        .border(3.dp, color = Color(123, 31, 162), shape = RoundedCornerShape(5))
        .padding(10.dp),
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        title()
        Box(
            modifier = Modifier.fillMaxWidth().height(1.dp).border(1.dp, color = Color(123, 31, 162))
        )
        Spacer(Modifier.height(10.dp))
        content()
    }
}

@Preview
@Composable
fun OutlinedContainerPreview() {
    OutlinedContainer(
        title = {
            Text(
                text = "Testas",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
        },
        content = { Text(text = "Testas") })
}