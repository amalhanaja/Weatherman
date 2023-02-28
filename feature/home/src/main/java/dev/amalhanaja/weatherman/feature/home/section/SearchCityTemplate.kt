package dev.amalhanaja.weatherman.feature.home.section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.amalhanaja.weatherman.core.designsystem.foundation.WMTheme
import dev.amalhanaja.weatherman.core.model.City
import dev.amalhanaja.weatherman.feature.home.CityListUiState
import dev.amalhanaja.weatherman.feature.home.R

@Composable
internal fun SearchCityTemplate(
    query: String,
    onQueryChange: (String) -> Unit,
    cityListUiState: CityListUiState,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    SearchBar(
        modifier = modifier,
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onQueryChange,
        active = active,
        onActiveChange = onActiveChange,
        placeholder = { Text(text = stringResource(R.string.placeholder_search_city)) },
        leadingIcon = {
            IconButton(
                onClick = { onActiveChange(false) }
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    ) {
        LazyColumn() {
            when (cityListUiState) {
                is CityListUiState.WithData -> selectableCityList(data = cityListUiState)
                else -> Unit
            }
        }
    }
}

private fun LazyListScope.selectableCityList(data: CityListUiState.WithData) {
    stickyHeader {
        Text(
            text = when {
                data.isFavoriteList -> stringResource(id = R.string.text_Favorites)
                else -> stringResource(id = R.string.text_Search_results)
            },
            modifier = Modifier
                .background(WMTheme.colorScheme.background)
                .fillMaxWidth()
                .padding(horizontal = WMTheme.spacings.l, vertical = WMTheme.spacings.m),
            style = WMTheme.typography.titleLarge,
        )
    }
    items(data.cities) { city -> CityItemComponent(city = city) }
}

@Composable
private fun CityItemComponent(
    city: City,
    trailingContent: (@Composable () -> Unit)? = null,
) {
    val displayableCity = listOf(city.name, city.state, city.country).filter(String::isNotBlank).joinToString()
    ListItem(
        modifier = Modifier.fillMaxWidth(),
        headlineText = { Text(text = displayableCity) },
        trailingContent = trailingContent,
    )
}
