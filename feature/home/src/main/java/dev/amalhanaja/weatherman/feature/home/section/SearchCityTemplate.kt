package dev.amalhanaja.weatherman.feature.home.section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.amalhanaja.weatherman.core.designsystem.foundation.WMTheme
import dev.amalhanaja.weatherman.core.model.City

@Composable
internal fun SearchCityTemplate(
    query: String,
    onQueryChange: (String) -> Unit,
    listTitle: String,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    cities: List<City>,
    modifier: Modifier = Modifier,
) {
    SearchBar(
        modifier = modifier,
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onQueryChange,
        active = active,
        onActiveChange = onActiveChange,
    ) {
        LazyColumn() {
            stickyHeader {
                Text(
                    text = listTitle,
                    modifier = Modifier
                        .background(WMTheme.colorScheme.background)
                        .fillMaxWidth()
                        .padding(horizontal = WMTheme.spacings.l, vertical = WMTheme.spacings.m),
                    style = WMTheme.typography.titleLarge,
                )
            }
            items(cities) { city -> CityItemComponent(city = city) }
        }
    }
}

@Composable
private fun CityItemComponent(
    city: City,
    trailingContent: (@Composable () -> Unit)? = null,
) {
    val displayableCity = with(city) { "${localName ?: name}, $country" }
    ListItem(
        modifier = Modifier.fillMaxWidth(),
        headlineText = { Text(text = displayableCity) },
        trailingContent = trailingContent,
    )
}
