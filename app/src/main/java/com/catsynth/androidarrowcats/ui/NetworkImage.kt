import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.onCommit
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.graphics.asImageAsset
import com.squareup.picasso.Picasso
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@Composable
fun NetworkImage(url: String, modifier: Modifier) {

    var image by remember { mutableStateOf<ImageAsset?>(null) }

    onCommit(url) {
        val picasso = Picasso.get()

        val target = object : com.squareup.picasso.Target  {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                image = bitmap?.asImageAsset()
            }
        }

        picasso
            .load(url)
            .into(target)

        onDispose {
            image = null
            picasso.cancelRequest(target)
        }
    }

    image?.let { Image(it, modifier) }
}
