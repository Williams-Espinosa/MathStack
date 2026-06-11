package com.williamsel.mathstack.ui.welcome.identifyyourareasofimprovement.presentacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.williamsel.mathstack.ui.theme.*

@Composable
fun IdentifyyourareasofimprovementScreen(
    onSaltar: () -> Unit = {},
    onSiguiente: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoPantalla)
    ) {
        TextButton(
            onClick = onSaltar,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 16.dp)
        ) {
            Text(
                text = "Saltar",
                color = TextoSecundario,
                fontSize = 16.sp
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
                    .background(AzulPrimario),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.BarChart,
                    contentDescription = "Gráfica de áreas",
                    tint = IconoBlanco,
                    modifier = Modifier.size(80.dp)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Identifica tus áreas\nde mejora",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal,
                textAlign = TextAlign.Center,
                lineHeight = 34.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "MathStack detecta tus fortalezas y debilidades mediante una evaluación diagnóstica.",
                fontSize = 15.sp,
                color = TextoSecundario,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(AzulIndicador)
                )
                repeat(2) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(IndicadorInactivo)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onSiguiente,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BotonPrimario)
            ) {
                Text(
                    text = "Siguiente  ›",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BotonTexto
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

