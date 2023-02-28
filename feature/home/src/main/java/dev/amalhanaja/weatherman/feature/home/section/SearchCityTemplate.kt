package dev.amalhanaja.weatherman.feature.home.section

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import dev.amalhanaja.weatherman.core.designsystem.component.ErrorComponent
import dev.amalhanaja.weatherman.core.designsystem.component.MessageComponent
import dev.amalhanaja.weatherman.core.designsystem.foundation.WMTheme
import dev.amalhanaja.weatherman.core.model.City
import dev.amalhanaja.weatherman.feature.home.CityListUiState
import dev.amalhanaja.weatherman.feature.home.R
import dev.amalhanaja.weatherman.core.designsystem.R as DesignResource

@Composable
internal fun SearchCityTemplate(
    query: String,
    onQueryChange: (String) -> Unit,
    cityListUiState: CityListUiState,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onFavoriteChange: (City, Boolean) -> Unit,
    onRetry: () -> Unit,
    onSelect: (City) -> Unit,
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
                is CityListUiState.WithData -> selectableCityList(
                    data = cityListUiState,
                    onFavoriteChange = onFavoriteChange,
                    onSelect = {
                        onSelect(it)
                        onActiveChange(false)
                    },
                )
                is CityListUiState.Empty -> item { Message(title = stringResource(R.string.message_no_favorites)) }
                is CityListUiState.NotFound -> item { Message(title = stringResource(id = R.string.message_search_not_found)) }
                is CityListUiState.Failed -> item {
                    ErrorComponent(
                        title = stringResource(id = DesignResource.string.title_error_general),
                        description = stringResource(id = DesignResource.string.message_error_general),
                        actionText = stringResource(id = DesignResource.string.action_try_again),
                        onActionClick = onRetry,
                    )
                }
            }
        }
    }
}

@Composable
private fun Message(title: String) {
    MessageComponent(
        modifier = Modifier
            .fillMaxWidth()
            .padding(WMTheme.spacings.jumbo),
        title = title,
        illustration = {
            Image(painter = rememberVectorPainter(image = Icons.Default.Warning), contentDescription = title)
        }
    )
}

private fun LazyListScope.selectableCityList(
    data: CityListUiState.WithData,
    onFavoriteChange: (City, Boolean) -> Unit,
    onSelect: (City) -> Unit,
) {
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
    items(data.citiesWithFavorite) { (city, isFavorite) ->
        CityItemComponent(
            city = city,
            modifier = Modifier.clickable { onSelect(city) }
        ) {
            IconButton(
                onClick = { onFavoriteChange(city, !isFavorite) }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = city.name,
                )
            }
        }
    }
}

@Composable
private fun CityItemComponent(
    city: City,
    modifier: Modifier = Modifier,
    trailingContent: (@Composable () -> Unit)? = null,
) {
    val displayableCity = listOf(city.name, city.state, city.country).filter(String::isNotBlank).joinToString()
    ListItem(
        modifier = modifier.fillMaxWidth(),
        headlineText = { Text(text = displayableCity) },
        trailingContent = trailingContent,
    )
}
