package ar.edu.utn.frba.mobile.turistapp.ui.locations_map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ar.edu.utn.frba.mobile.turistapp.R
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.locations_list.LocationListPreview
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.locations_list.Title
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.locations_list.testLocationList
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.map.MapScreen
import kotlinx.coroutines.launch



@Composable
@Preview
fun LocationsMapScreen() {
    BottomSheetScafold(
        contentUnderSheet = {
            MapScreen()
        },
        contentToShowPartially = {
            Title(stringResource(R.string.locations))
        },
        hiddenContent = {
            //TODO: Cambiar por la lista de locations real
            LocationListPreview()
        }
    )
}


@Composable
@ExperimentalMaterial3Api
fun ModalBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    containerColor: Color = BottomSheetDefaults.ContainerColor,
    contentColor: Color = contentColorFor(containerColor),
    tonalElevation: Dp = BottomSheetDefaults.Elevation,
    scrimColor: Color = BottomSheetDefaults.ScrimColor,
    dragHandle: (@Composable () -> Unit)? = { BottomSheetDefaults.DragHandle() },
    content: @Composable ColumnScope.() -> Unit
): Unit {
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetScafold(contentUnderSheet: @Composable() () -> Unit,
                               contentToShowPartially: @Composable() () -> Unit,
                               hiddenContent: @Composable() () -> Unit) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val partialSheetHeigth = 64.dp

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = partialSheetHeigth,
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(partialSheetHeigth),
                contentAlignment = Alignment.Center
            ) {
                contentToShowPartially()
            }
            Column(
                Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                hiddenContent()
                Spacer(Modifier.height(20.dp))
            }
        }) {
        contentUnderSheet()
    }
}



