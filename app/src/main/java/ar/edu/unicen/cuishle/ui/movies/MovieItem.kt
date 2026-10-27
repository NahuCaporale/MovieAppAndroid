package ar.edu.unicen.cuishle.ui.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unicen.cuishle.R
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import java.util.Locale

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem(
    id: Int,
    voteAvg: String,
    title: String,
    imageUrl: String,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick(id) }
            .padding(8.dp)
    ) {
        Box {

            GlideImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 3f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.onSurfaceVariant),
                failure = placeholder(R.drawable.broken_image),
                contentScale = ContentScale.Crop,
                model = imageUrl,
                contentDescription = title
            )
            /**
             * Version vieja con los loaders(podia generar tirones o un loop infinito con el placeholders)
             *  loading = placeholder {
             *                     Box(
             *                         modifier = Modifier
             *                             .fillMaxSize()
             *                             .background(MaterialTheme.colorScheme.surface),
             *                         contentAlignment = Alignment.Center
             *                     ) {
             *                         CircularProgressIndicator(
             *                             modifier = Modifier.size(24.dp),
             *                             strokeWidth = 2.dp
             *                         )
             *                     }
             *                 }, failure = placeholder {
             *                     Box(
             *                         modifier = Modifier
             *                             .fillMaxSize()
             *                             .background(MaterialTheme.colorScheme.surfaceVariant),
             *                         contentAlignment = Alignment.Center
             *                     ) {
             *                         Icon(
             *                             imageVector = Icons.Default.BrokenImage,
             *                             contentDescription = null,
             *                             tint = MaterialTheme.colorScheme.onSurfaceVariant
             *                         )
             *                     }
             *                 }
             * */
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .background(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = Modifier.size(10.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = voteAvg,
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            modifier = Modifier.padding(bottom = 8.dp),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

