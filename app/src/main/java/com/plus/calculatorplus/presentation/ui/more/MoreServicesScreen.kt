@file:OptIn(ExperimentalFoundationApi::class)

package com.plus.calculatorplus.presentation.ui.more

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.plus.calculatorplus.presentation.components.CustomCard
import com.plus.calculatorplus.presentation.components.ScreenScaffold
import com.plus.calculatorplus.presentation.icons.account_balance_wallet
import com.plus.calculatorplus.presentation.icons.acute
import com.plus.calculatorplus.presentation.icons.cake
import com.plus.calculatorplus.presentation.icons.call_split
import com.plus.calculatorplus.presentation.icons.close
import com.plus.calculatorplus.presentation.icons.credit_card_clock
import com.plus.calculatorplus.presentation.icons.loan
import com.plus.calculatorplus.presentation.icons.monitor_weight
import com.plus.calculatorplus.presentation.icons.more
import com.plus.calculatorplus.presentation.icons.percent_discount
import com.plus.calculatorplus.presentation.icons.price_check
import com.plus.calculatorplus.presentation.icons.request_quote
import com.plus.calculatorplus.presentation.icons.retirement
import com.plus.calculatorplus.presentation.icons.savings
import com.plus.calculatorplus.presentation.icons.search
import com.plus.calculatorplus.presentation.icons.sip
import com.plus.calculatorplus.presentation.icons.swap_horizontal_circle
import com.plus.calculatorplus.presentation.icons.trending_down
import com.plus.calculatorplus.presentation.icons.trending_up
import com.plus.calculatorplus.presentation.navigation.Navigator
import com.plus.calculatorplus.presentation.navigation.Screen
import com.plus.calculatorplus.presentation.theme.CalculatorPlusTheme
import kotlinx.collections.immutable.persistentListOf

enum class ServiceCategory(val label: String) {
    INVESTMENT("Investments"),
    LOANS("Loans"),
    TAX_SHOPPING("Tax & Shopping"),
    PLANNING("Planning"),
    UTILITIES("Utilities"),
    Health("Health")
}

data class ServiceItem(
    val title: String,
    val icon: ImageVector,
    val category: ServiceCategory,
    val onClick: () -> Unit
)

// Simple local MVI for the MoreServices screen
data class MoreServicesState(
    val query: String = "",
    val selectedCategory: ServiceCategory? = null,
    val searchVisible: Boolean = false
)

sealed interface MoreServicesIntent {
    object ToggleSearch : MoreServicesIntent
    data class UpdateQuery(val query: String) : MoreServicesIntent
    data class SelectCategory(val category: ServiceCategory?) : MoreServicesIntent
    object ClearQuery : MoreServicesIntent
}

private val calculatorRoutes = setOf<NavKey>(
    Screen.SipScreen,
    Screen.BmiScreen,
    Screen.EmiScreen,
    Screen.DiscountScreen,
    Screen.FdScreen,
    Screen.SwpScreen,
    Screen.RetirementScreen,
    Screen.DividendScreen,
    Screen.CagrScreen,
    Screen.TaxScreen,
    Screen.LumpsumScreen,
    Screen.InflationScreen,
    Screen.BillSplitterScreen,
    Screen.UnitConverterScreen,
    Screen.AgeDateScreen,
    Screen.LoanAffordabilityScreen,
    Screen.LoanPrepaymentScreen
)

private fun Navigator.navigateReplacing(route: NavKey) {
    state.backStacks.values.forEach { stack ->
        calculatorRoutes.forEach { stack.remove(it) }
    }
    navigate(route)
}

@Composable
fun MoreServicesScreen(navigator: Navigator, modifier: Modifier = Modifier) {
    MoreServices(
        onServiceClick = { navigator.navigateReplacing(it) }, modifier = modifier
    )
}

@Composable
fun MoreServices(
    onServiceClick: (NavKey) -> Unit, modifier: Modifier = Modifier
) {
    // local MVI state
    val state = remember { mutableStateOf(MoreServicesState()) }
    val focusManager = LocalFocusManager.current
    val gridState = rememberLazyGridState()

    fun send(intent: MoreServicesIntent) {
        when (intent) {
            is MoreServicesIntent.ToggleSearch -> {
                val newVisible = !state.value.searchVisible
                state.value = state.value.copy(
                    searchVisible = newVisible,
                    // clear query when hiding search
                    query = if (!newVisible) "" else state.value.query
                )
                if (!newVisible) focusManager.clearFocus()
            }

            is MoreServicesIntent.UpdateQuery -> state.value =
                state.value.copy(query = intent.query)

            is MoreServicesIntent.SelectCategory -> state.value =
                state.value.copy(selectedCategory = intent.category)

            is MoreServicesIntent.ClearQuery -> state.value = state.value.copy(query = "")
        }
    }

    ScreenScaffold(
        title = "More Tools",
        subtitle = "Specialized calculators for all your needs",
        icon = more,
        actionIcon = search,
        onActionClick = { send(MoreServicesIntent.ToggleSearch) },
        actionIconTint = if (state.value.searchVisible) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
        actionIconBgColor = if (state.value.searchVisible) MaterialTheme.colorScheme.primary else Color.Transparent,
        modifier = modifier
    ) { paddingValues ->
        val services = remember(onServiceClick) {
            persistentListOf(
                ServiceItem(
                    "SIP",
                    sip,
                    ServiceCategory.INVESTMENT
                ) { onServiceClick(Screen.SipScreen) },
                ServiceItem(
                    "SWP",
                    sip,
                    ServiceCategory.INVESTMENT
                ) { onServiceClick(Screen.SwpScreen) },
                ServiceItem(
                    "FD",
                    savings,
                    ServiceCategory.INVESTMENT
                ) { onServiceClick(Screen.FdScreen) },
                ServiceItem(
                    "Lump Sum",
                    account_balance_wallet,
                    ServiceCategory.INVESTMENT
                ) { onServiceClick(Screen.LumpsumScreen) },
                ServiceItem("Dividend", price_check, ServiceCategory.INVESTMENT) {
                    onServiceClick(
                        Screen.DividendScreen
                    )
                },
                ServiceItem(
                    "CAGR",
                    trending_up,
                    ServiceCategory.INVESTMENT
                ) { onServiceClick(Screen.CagrScreen) },
                ServiceItem(
                    "EMI",
                    credit_card_clock,
                    ServiceCategory.LOANS
                ) { onServiceClick(Screen.EmiScreen) },
                ServiceItem(
                    "Loan Eligibility",
                    loan,
                    ServiceCategory.LOANS
                ) { onServiceClick(Screen.LoanAffordabilityScreen) },
                ServiceItem(
                    "Loan Prepayment",
                    acute,
                    ServiceCategory.LOANS
                ) { onServiceClick(Screen.LoanPrepaymentScreen) },
                ServiceItem(
                    "Discount",
                    percent_discount,
                    ServiceCategory.TAX_SHOPPING
                ) { onServiceClick(Screen.DiscountScreen) },
                ServiceItem(
                    "Tax / GST",
                    request_quote,
                    ServiceCategory.TAX_SHOPPING
                ) { onServiceClick(Screen.TaxScreen) },
                ServiceItem(
                    "Bill Splitter",
                    call_split,
                    ServiceCategory.TAX_SHOPPING
                ) { onServiceClick(Screen.BillSplitterScreen) },
                ServiceItem("Retirement", retirement, ServiceCategory.PLANNING) {
                    onServiceClick(
                        Screen.RetirementScreen
                    )
                },
                ServiceItem("Inflation", trending_down, ServiceCategory.PLANNING) {
                    onServiceClick(
                        Screen.InflationScreen
                    )
                },
                ServiceItem("Unit Converter", swap_horizontal_circle, ServiceCategory.UTILITIES) {
                    onServiceClick(
                        Screen.UnitConverterScreen
                    )
                },
                ServiceItem("Age & Date", cake, ServiceCategory.UTILITIES) {
                    onServiceClick(
                        Screen.AgeDateScreen
                    )
                },
                ServiceItem(
                    "BMI",
                    monitor_weight,
                    ServiceCategory.Health
                ) { onServiceClick(Screen.BmiScreen) }
            )
        }

        val grouped by remember(services, state.value.query, state.value.selectedCategory) {
            derivedStateOf {
                @Suppress("DerivedStateOfCandidate")
                val filtered = services.filter { item ->
                    (state.value.selectedCategory == null || item.category == state.value.selectedCategory) &&
                              (state.value.query.isBlank() || item.title.contains(
                                  state.value.query.trim(),
                                  ignoreCase = true
                              ))
                }
                @Suppress("DerivedStateOfCandidate")
                ServiceCategory.entries.mapNotNull { category ->
                    val items = filtered.filter { it.category == category }
                    if (items.isEmpty()) null else category to items
                }
            }
        }

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
            AnimatedVisibility(state.value.searchVisible) {
                OutlinedTextField(
                    value = state.value.query,
                    onValueChange = { send(MoreServicesIntent.UpdateQuery(it)) },
                    placeholder = { Text("Search calculators") },
                    trailingIcon = {
                        if (state.value.query.isNotEmpty()) {
                            IconButton(onClick = { send(MoreServicesIntent.ClearQuery) }) {
                                Icon(
                                    imageVector = close,
                                    contentDescription = "Clear search"
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = CircleShape,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                )
            }

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item(key = "filter_all", contentType = "FilterChip") {
                    FilterChip(
                        selected = state.value.selectedCategory == null,
                        onClick = { send(MoreServicesIntent.SelectCategory(null)) },
                        label = { Text("All") },
                        shape = CircleShape,
                        modifier = Modifier.animateItem()
                    )
                }
                items(
                    ServiceCategory.entries,
                    key = { it.name },
                    contentType = { "${it.name}_FilterChip" }) { category ->
                    FilterChip(
                        selected = state.value.selectedCategory == category,
                        onClick = {
                            send(
                                MoreServicesIntent.SelectCategory(
                                    if (state.value.selectedCategory == category) null else category
                                )
                            )
                        },
                        label = { Text(category.label) },
                        shape = CircleShape,
                        modifier = Modifier.animateItem()
                    )
                }
            }

            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Adaptive(minSize = 140.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (grouped.isEmpty()) {
                    item(span = { GridItemSpan(maxLineSpan) }, contentType = "Empty") {
                        Text(
                            text = "No calculators match \"${state.value.query}\"",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .padding(vertical = 24.dp)
                                .animateItem()
                        )
                    }
                }

                grouped.forEach { (category, items) ->
                    stickyHeader(
                        key = "header_${category.name}",
                        contentType = "Header"
                    ) {
                        CategoryHeader(title = category.label, modifier = Modifier.animateItem())
                    }

                    items(
                        items = items,
                        key = { it.title },
                        contentType = { "ServiceItem" }
                    ) { service ->
                        CustomCard(
                            backgroundColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                            modifier = Modifier.animateItem(),
                            icon = service.icon,
                            title = service.title,
                            onClick = service.onClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryHeader(title: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MoreServicesScreenPreview() {
    CalculatorPlusTheme {
        Surface {
            MoreServices(
                onServiceClick = {})
        }
    }
}
