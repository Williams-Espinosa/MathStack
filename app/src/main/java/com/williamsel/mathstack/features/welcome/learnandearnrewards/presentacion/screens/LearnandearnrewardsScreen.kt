package com.williamsel.mathstack.ui.welcome.learnandearnrewards.presentacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.williamsel.mathstack.ui.theme.*

@Composable
fun LearnandearnrewardsScreen(
    onSaltar: () -> Unit = {},
    onComenzar: () -> Unit = {}
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
                modifier = Modifier.size(220.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(AmarilloTrofeo, NaranjaTrofeo)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = "Trofeo",
                        tint = IconoBlanco,
                        modifier = Modifier.size(80.dp)
                    )
                }

                Surface(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(x = 20.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = AzulNivel
                ) {
                    Text(
                        text = "NIVEL 12",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = BlancoPuro
                    )
                }

                Surface(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .offset(x = (-20).dp, y = (-20).dp),
                    shape = RoundedCornerShape(12.dp),
                    color = BlancoPuro,
                    shadowElevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.MonetizationOn,
                            contentDescription = null,
                            tint = AmarilloTrofeo,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Column {
                            Text("Monedas", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextoPrincipal)
                            Text("+120", fontSize = 10.sp, color = TextoSecundario)
                        }
                    }
                }

                Surface(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .offset(x = 20.dp, y = 20.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = BlancoPuro,
                    shadowElevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = null,
                            tint = NaranjaTrofeo,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Column {
                            Text("Racha", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextoPrincipal)
                            Text("5 días", fontSize = 10.sp, color = TextoSecundario)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Aprende y gana\nrecompensas",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal,
                textAlign = TextAlign.Center,
                lineHeight = 34.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Mantén tu racha y desbloquea recompensas exclusivas.",
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
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(IndicadorInactivo)
                    )
                }
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(AzulIndicador)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onComenzar,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BotonPrimario)
            ) {
                Text(
                    text = "Comenzar  ›",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BotonTexto
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
