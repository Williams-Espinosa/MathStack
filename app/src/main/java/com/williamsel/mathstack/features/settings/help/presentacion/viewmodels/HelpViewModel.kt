package com.williamsel.mathstack.features.settings.help.presentacion.viewmodels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.lifecycle.ViewModel
import com.williamsel.mathstack.features.settings.help.presentacion.screens.FaqCategory
import com.williamsel.mathstack.features.settings.help.presentacion.screens.FaqItem
import com.williamsel.mathstack.features.settings.help.presentacion.screens.HelpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HelpViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(HelpUiState(categories = getInitialFaqs()))
    val uiState: StateFlow<HelpUiState> = _uiState.asStateFlow()

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onToggleExpand(categoryTitle: String, question: String) {
        _uiState.update { state ->
            state.copy(
                categories = state.categories.map { category ->
                    if (category.title == categoryTitle) {
                        category.copy(
                            items = category.items.map { item ->
                                if (item.question == question) {
                                    item.copy(isExpanded = !item.isExpanded)
                                } else item
                            }
                        )
                    } else category
                }
            )
        }
    }

    private fun getInitialFaqs(): List<FaqCategory> = listOf(
        FaqCategory(
            title = "Cuenta",
            icon = Icons.Outlined.Person,
            items = listOf(
                FaqItem(
                    question = "¿Cómo cambio mi contraseña?",
                    answer = "Ve a Configuración > Gestión de cuenta > Cambiar contraseña. Necesitarás tu contraseña actual para confirmar el cambio."
                ),
                FaqItem(
                    question = "¿Puedo cambiar mi correo electrónico?",
                    answer = "Por el momento, el correo electrónico no puede ser cambiado una vez registrada la cuenta."
                ),
                FaqItem(
                    question = "¿Cómo elimino mi cuenta?",
                    answer = "Puedes solicitar la eliminación de tu cuenta desde la sección de Gestión de cuenta."
                )
            )
        ),
        FaqCategory(
            title = "Diagnóstico",
            icon = Icons.Outlined.CenterFocusStrong,
            items = listOf(
                FaqItem(
                    question = "¿Qué es el examen diagnóstico?",
                    answer = "Es una evaluación inicial que identifica tus fortalezas y debilidades en diferentes áreas matemáticas para crear una ruta personalizada."
                ),
                FaqItem(
                    question = "¿Puedo repetir el diagnóstico?",
                    answer = "Sí, puedes realizar el diagnóstico cada 30 días para actualizar tu ruta de aprendizaje según tu progreso."
                )
            )
        ),
        FaqCategory(
            title = "Rutas de Aprendizaje",
            icon = Icons.Outlined.School,
            items = listOf(
                FaqItem(
                    question = "¿Cómo funcionan las rutas?",
                    answer = "Las rutas se generan automáticamente según tu diagnóstico. Cada lección se desbloquea al completar la anterior."
                ),
                FaqItem(
                    question = "¿Puedo saltar lecciones?",
                    answer = "No, las lecciones deben completarse en orden para asegurar que construyes una base sólida de conocimientos."
                )
            )
        ),
        FaqCategory(
            title = "Retos",
            icon = Icons.Outlined.EmojiEvents,
            items = listOf(
                FaqItem(
                    question = "¿Qué son los retos semanales?",
                    answer = "Son desafíos temporales con objetivos específicos. Al completarlos ganas monedas y XP extra."
                ),
                FaqItem(
                    question = "¿Puedo hacer varios retos a la vez?",
                    answer = "Sí, puedes participar en múltiples retos simultáneamente."
                )
            )
        ),
        FaqCategory(
            title = "Grupos",
            icon = Icons.Outlined.Groups,
            items = listOf(
                FaqItem(
                    question = "¿Cómo creo un grupo?",
                    answer = "Ve a la sección Grupos y pulsa el botón +. Necesitas definir nombre, descripción, materia y máximo de miembros."
                ),
                FaqItem(
                    question = "¿Puedo salir de un grupo?",
                    answer = "Sí, puedes abandonar cualquier grupo desde la configuración del mismo."
                )
            )
        ),
        FaqCategory(
            title = "Tienda",
            icon = Icons.Outlined.Storefront,
            items = listOf(
                FaqItem(
                    question = "¿Cómo gano monedas?",
                    answer = "Ganas monedas completando lecciones, ejercicios y retos. También las recibes por mantener tu racha."
                ),
                FaqItem(
                    question = "¿Puedo comprar monedas?",
                    answer = "No, las monedas solo se ganan a través del aprendizaje y participación en la app."
                )
            )
        )
    )
}
