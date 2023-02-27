package dev.amalhanaja.weatherman.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.amalhanaja.weatherman.core.designsystem.foundation.WMTheme
import dev.amalhanaja.weatherman.core.model.City
import dev.amalhanaja.weatherman.feature.home.section.SearchCityTemplate

@Composable
fun HomeRoute() {
    val (isCitySelectionActive, setIsCitySelectionActive) = remember { mutableStateOf(false) }
    return HomeScreen(
        query = "",
        onQueryChange = {},
        citiesSectionTitle = "Search results",
        cities = (1..20).map { City("Name $it", null, "ID", 0.0, 0.0) },
        isCitySelectionActive = isCitySelectionActive,
        onCitySelectionActiveChange = setIsCitySelectionActive,
    )
}

@Composable
internal fun HomeScreen(
    query: String,
    onQueryChange: (String) -> Unit,
    citiesSectionTitle: String,
    cities: List<City>,
    isCitySelectionActive: Boolean,
    onCitySelectionActiveChange: (Boolean) -> Unit,
) {
    Scaffold(
        topBar = {
            if (isCitySelectionActive) return@Scaffold
            TopAppBar(
                title = {
                    SelectedLocationComponent(
                        modifier = Modifier.clickable { onCitySelectionActiveChange(true) }
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isCitySelectionActive) {
                SearchCityTemplate(
                    modifier = Modifier.fillMaxWidth(),
                    query = query,
                    onQueryChange = onQueryChange,
                    listTitle = citiesSectionTitle,
                    active = true,
                    onActiveChange = onCitySelectionActiveChange,
                    cities = cities,
                )
            }
            Spacer(modifier = Modifier.height(WMTheme.spacings.xl))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                HeroComponent(modifier = Modifier.fillMaxWidth())
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(WMTheme.spacings.xxl)
            ) {
                SectionAdditionalInformation(modifier = Modifier.fillMaxWidth())
            }
            SectionDailyForecast(modifier = Modifier.padding(horizontal = WMTheme.spacings.xxl))
            Spacer(modifier = Modifier.height(WMTheme.spacings.xl))
        }
    }
}

@Composable
private fun SelectedLocationComponent(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Location")
        Spacer(modifier = Modifier.width(WMTheme.spacings.m))
        Text(text = "Surabaya")
        Spacer(modifier = Modifier.width(WMTheme.spacings.m))
        Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Select Location")
    }
}

@Composable
private fun HeroComponent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(96.dp),
            painter = rememberVectorPainter(image = Icons.Default.AccountBox),
            contentDescription = "Weather",
        )
        Spacer(modifier = Modifier.height(WMTheme.spacings.l))
        Text(text = "Sunny", style = WMTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(WMTheme.spacings.s))
        Row() {
            Text(text = "H: 31º", style = WMTheme.typography.labelLarge)
            Spacer(modifier = Modifier.width(WMTheme.spacings.s))
            Text(text = "L: 24º", style = WMTheme.typography.labelLarge)
        }
    }
}

@Composable
private fun SectionAdditionalInformation(
    modifier: Modifier = Modifier,
) {
    OutlinedCard(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(WMTheme.spacings.l),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            AdditionalInformation(painter = rememberVectorPainter(image = Icons.Default.Build), text = "6%")
            AdditionalInformation(painter = rememberVectorPainter(image = Icons.Default.Build), text = "6%")
            AdditionalInformation(painter = rememberVectorPainter(image = Icons.Default.Build), text = "6%")
        }
    }
}

@Composable
private fun AdditionalInformation(
    painter: Painter,
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(14.dp),
            painter = painter,
            contentDescription = text,
        )
        Spacer(modifier = Modifier.width(WMTheme.spacings.s))
        Text(text = text, style = WMTheme.typography.bodyMedium)
    }
}

@Composable
fun SectionDailyForecast(
    modifier: Modifier = Modifier,
) {
    val list = listOf<String>()
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = "Daily Forecast")
        Spacer(modifier = Modifier.height(WMTheme.spacings.l))
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            repeat(3) {
                if (it != 0) Spacer(modifier = Modifier.width(WMTheme.spacings.l))
                WeatherCard(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun WeatherCard(
    modifier: Modifier = Modifier,
) {
    OutlinedCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(WMTheme.spacings.l),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Mon",
                style = WMTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(WMTheme.spacings.m))
            Image(
                modifier = Modifier.size(48.dp),
                painter = rememberVectorPainter(image = Icons.Default.AccountBox),
                contentDescription = "Weather"
            )
            Spacer(modifier = Modifier.height(WMTheme.spacings.m))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = "31º", style = WMTheme.typography.labelMedium)
                Text(text = "24º", style = WMTheme.typography.labelMedium)
            }
        }
    }
}
