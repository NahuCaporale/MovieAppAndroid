package ar.edu.unicen.cuishle.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

/**
 * Componente de pestaña personalizado diseñado según los requerimientos estéticos del mockup.
 *
 * Creado con asistencia de IA para adaptar el estilo visual y las proporciones
 * que habia diseñado en un mockup. el diseño que proporcionaba el componente de google no me gustaba.
 */

@Composable
fun MockupTab(
    title: String,
    icon: ImageVector?,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val color = if (selected) {
        MaterialTheme.colorScheme.onBackground
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Column(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .selectable(selected = selected, onClick = onClick, role = Role.Tab),
    ) {
        Row(
            modifier = Modifier.padding(vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (icon != null) {
                Icon(
                    imageVector = if (selected) Icons.Filled.Bookmark else
                        icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(18.dp),
                )
                Spacer(Modifier.width(6.dp))
            }
            Text(
                text = title.uppercase(),
                color = color,
                style = MaterialTheme.typography.labelLarge,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(if (selected) color else Color.Transparent),
        )
    }
}