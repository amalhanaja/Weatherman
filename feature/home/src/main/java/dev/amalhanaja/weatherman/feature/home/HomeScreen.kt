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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.amalhanaja.weatherman.core.designsystem.component.ErrorComponent
import dev.amalhanaja.weatherman.core.designsystem.foundation.WMTheme
import dev.amalhanaja.weatherman.core.model.City
import dev.amalhanaja.weatherman.core.model.Weather
import dev.amalhanaja.weatherman.feature.home.section.SearchCityTemplate
import java.time.format.TextStyle
import java.util.Locale
import dev.amalhanaja.weatherman.core.designsystem.R as DesignResource

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val (isCitySelectionActive, setIsCitySelectionActive) = remember { mutableStateOf(false) }
    val cityListUiState by homeViewModel.cityListUiState.collectAsStateWithLifecycle()
    val currentCity by homeViewModel.currentCityState.collectAsStateWithLifecycle()
    val weatherDataUiState by homeViewModel.weatherData.collectAsStateWithLifecycle()
    return HomeScreen(
        query = homeViewModel.searchQuery,
        onQueryChange = homeViewModel::updateSearchQuery,
        cityListUiState = cityListUiState,
        isCitySelectionActive = isCitySelectionActive,
        onCitySelectionActiveChange = {
            setIsCitySelectionActive(it)
            if (it) return@HomeScreen
            homeViewModel.updateSearchQuery("")
        },
        onRetryCityList = homeViewModel::retrySearch,
        onCityFavoriteChange = homeViewModel::onFavoriteCityChange,
        onSelectCity = homeViewModel::selectCity,
        currentCity = currentCity,
        weatherDataUiState = weatherDataUiState,
        onRetryGetWeather = homeViewModel::getWeatherData
    )
}

@Composable
internal fun HomeScreen(
    query: String,
    onQueryChange: (String) -> Unit,
    cityListUiState: CityListUiState,
    onRetryCityList: () -> Unit,
    onCityFavoriteChange: (City, Boolean) -> Unit,
    isCitySelectionActive: Boolean,
    onCitySelectionActiveChange: (Boolean) -> Unit,
    currentCity: City,
    onSelectCity: (City) -> Unit,
    weatherDataUiState: WeatherDataUiState,
    onRetryGetWeather: () -> Unit,
) {
    Scaffold(
        topBar = {
            if (isCitySelectionActive) return@Scaffold
            TopAppBar(
                title = {
                    SelectedLocationComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCitySelectionActiveChange(true) },
                        city = currentCity.name,
                    )
                },
                modifier = Modifier.fillMaxWidth()
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
                    cityListUiState = cityListUiState,
                    active = true,
                    onActiveChange = onCitySelectionActiveChange,
                    onRetry = onRetryCityList,
                    onFavoriteChange = onCityFavoriteChange,
                    onSelect = onSelectCity
                )
            }
            Spacer(modifier = Modifier.height(WMTheme.spacings.xl))
            when (weatherDataUiState) {
                is WeatherDataUiState.Error -> weatherDataUiState.Composable(onRetry = onRetryGetWeather)
                is WeatherDataUiState.Loading -> weatherDataUiState.Composable()
                is WeatherDataUiState.WithData -> {
                    val data = weatherDataUiState.data
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        HeroComponent(modifier = Modifier.fillMaxWidth(), weather = data.first())
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(WMTheme.spacings.xxl)
                    ) {
                        SectionAdditionalInformation(modifier = Modifier.fillMaxWidth(), weather = data.first())
                    }
                    SectionDailyForecast(modifier = Modifier.padding(horizontal = WMTheme.spacings.xxl), weathers = data)
                    Spacer(modifier = Modifier.height(WMTheme.spacings.xl))
                }
            }
        }
    }
}

@Composable
private fun WeatherDataUiState.Error.Composable(onRetry: () -> Unit) {
    return ErrorComponent(
        modifier = Modifier.fillMaxSize(),
        title = stringResource(id = DesignResource.string.title_error_general),
        actionText = stringResource(id = DesignResource.string.action_try_again),
        onActionClick = onRetry,
        illustration = {
            Image(painter = rememberVectorPainter(image = Icons.Default.Warning), contentDescription = null)
        }
    )
}

@Composable
private fun WeatherDataUiState.Loading.Composable() {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun SelectedLocationComponent(
    city: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Location")
        Spacer(modifier = Modifier.width(WMTheme.spacings.m))
        Text(text = city, overflow = TextOverflow.Ellipsis)
        Spacer(modifier = Modifier.width(WMTheme.spacings.m))
        Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Select Location")
        Spacer(modifier = Modifier.width(WMTheme.spacings.xxl))
    }
}

@Composable
private fun HeroComponent(
    modifier: Modifier = Modifier,
    weather: Weather,
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
        Text(text = weather.name, style = WMTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(WMTheme.spacings.s))
        Row() {
            Text(text = "H: ${weather.tempMinMax.second.toInt()}º", style = WMTheme.typography.labelLarge)
            Spacer(modifier = Modifier.width(WMTheme.spacings.s))
            Text(text = "L: ${weather.tempMinMax.first.toInt()}º", style = WMTheme.typography.labelLarge)
        }
    }
}

@Composable
private fun SectionAdditionalInformation(
    modifier: Modifier = Modifier,
    weather: Weather,
) {
    OutlinedCard(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(WMTheme.spacings.l),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            AdditionalInformation(label = stringResource(R.string.label_humidity), text = "${weather.humidity}")
            AdditionalInformation(label = stringResource(R.string.label_windspeed), text = "%.2f".format(weather.windSpeed))
        }
    }
}

@Composable
private fun AdditionalInformation(
    label: String,
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = label, style = WMTheme.typography.labelLarge)
        Spacer(modifier = Modifier.width(WMTheme.spacings.s))
        Text(text = text, style = WMTheme.typography.bodyMedium)
    }
}

@Composable
fun SectionDailyForecast(
    modifier: Modifier = Modifier,
    weathers: List<Weather>,
) {
    val list = listOf<String>()
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = "Daily Forecast")
        Spacer(modifier = Modifier.height(WMTheme.spacings.l))
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            weathers.take(3).forEachIndexed { index, weather ->
                if (index != 0) Spacer(modifier = Modifier.width(WMTheme.spacings.l))
                WeatherCard(modifier = Modifier.weight(1f), weather = weather)
            }
        }
    }
}

@Composable
private fun WeatherCard(
    modifier: Modifier = Modifier,
    weather: Weather,
) {
    OutlinedCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(WMTheme.spacings.l),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = weather.date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
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
                Text(text = "${weather.tempMinMax.second.toInt()}º", style = WMTheme.typography.labelMedium)
                Text(text = "${weather.tempMinMax.first.toInt()}º", style = WMTheme.typography.labelMedium)
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(R.string.label_humidity), style = WMTheme.typography.labelSmall)
                Spacer(modifier = Modifier.width(WMTheme.spacings.s))
                Text(text = "${weather.humidity}", style = WMTheme.typography.bodySmall)
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(R.string.label_windspeed), style = WMTheme.typography.labelSmall)
                Spacer(modifier = Modifier.width(WMTheme.spacings.s))
                Text(text = "%.2f".format(weather.windSpeed), style = WMTheme.typography.bodySmall)
            }
        }
    }
}
