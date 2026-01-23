package com.example.parametrable.features.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.example.parametrable.R
import com.example.parametrable.config_wl.MerchantSectionId
import com.example.parametrable.config_wl.MerchantStepConfig
import com.example.parametrable.config_wl.MerchantStepId
import com.example.parametrable.config_wl.enabledSteps
import com.example.parametrable.config_wl.fieldEnabled
import com.example.parametrable.config_wl.section
import com.example.parametrable.util.Config

// -------------------- Screen --------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateMerchant(
    config: Config,
    onBack: () -> Unit) {

    // CALL THE CREATE MERCHANT SCREEN CONFIGURATION
    val screenCfg = config.createMerchantScreen

    // CHECK ENABLED STEPS
    val enabledSteps = screenCfg.enabledSteps()

    // JSON FALLBACK
    val stepsToRender = enabledSteps.ifEmpty {
        listOf(
        MerchantStepConfig(MerchantStepId.CONTACT, true, "step_contact"),
        MerchantStepConfig(MerchantStepId.MERCHANT, true, "step_merchant"),
        MerchantStepConfig(MerchantStepId.BANK, true, "step_bank"),
        MerchantStepConfig(MerchantStepId.SEGMENTATION, true, "step_segmentation"),
    )
    }

    var activeStep by remember { mutableIntStateOf(0) }
    val steps = listOf(
        stringResource(R.string.step_contact),
        stringResource(R.string.step_merchant),
        stringResource(R.string.step_bank),
        stringResource(R.string.step_segmentation),
    )


    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    var form by remember { mutableStateOf(CreateMerchantFormState()) }

    // Build titles from titleKey (strings.xml)
    val titles = stepsToRender.map { step ->
        when (step.titleKey) {
            "step_contact" -> stringResource(R.string.step_contact)
            "step_merchant" -> stringResource(R.string.step_merchant)
            "step_bank" -> stringResource(R.string.step_bank)
            "step_segmentation" -> stringResource(R.string.step_segmentation)
            else -> step.titleKey // fallback (shows raw if unknown)
        }
    }

    Scaffold(
//        topBar = {
//            TopAppBar(
//                navigationIcon = {
//                    IconButton(onClick = onBack) {
//                        Icon(Icons.AutoMirrored.Outlined.ArrowBack, "Back")
//                    }
//                }
//            )
//        },
        bottomBar = {
            BottomNavBar(
                activeStep = activeStep,
                stepsCount = steps.size,
                onPrev = {
                    if (activeStep > 0) {
                        activeStep--
                        focusManager.clearFocus()
                    }
                },
                onNext = {
                    if (activeStep < steps.lastIndex) {
                        activeStep++
                        focusManager.clearFocus()
                    } else {
                        // Submit logic (use `form`)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = padding.calculateBottomPadding())
                .verticalScroll(scrollState)
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
        ) {
            stepsToRender.forEachIndexed { index, step ->
                VerticalStep(
                    stepIndex = index,
                    title = titles[index],
                    isActive = activeStep == index,
                    isCompleted = activeStep > index,
                    isLast = index == stepsToRender.lastIndex,
                    onStepClick = { if (index < activeStep) activeStep = index },
                ) {
                    when (step.id) {
                        MerchantStepId.CONTACT -> ContactForm(
                            stepConfig = step,
                            state = form.contact,
                            onStateChange = { form = form.copy(contact = it) }
                        )

                        MerchantStepId.MERCHANT -> MerchantForm(
                            stepConfig = step,
                            state = form.merchant,
                            onStateChange = { form = form.copy(merchant = it) }
                        )

                        MerchantStepId.BANK -> BankForm(
                            stepConfig = step,
                            state = form.bank,
                            onStateChange = { form = form.copy(bank = it) }
                        )

                        MerchantStepId.SEGMENTATION -> SegmentationForm(
                            stepConfig = step,
                            state = form.segmentation,
                            onStateChange = { form = form.copy(segmentation = it) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

// -------------------- State --------------------

@Immutable
data class CreateMerchantFormState(
    val contact: ContactState = ContactState(),
    val merchant: MerchantState = MerchantState(),
    val bank: BankState = BankState(),
    val segmentation: SegmentationState = SegmentationState(),
)

@Immutable
data class ContactState(
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",
    val cin: String = "",
)

@Immutable
data class MerchantState(
    val legalStatus: String = "PHYSICAL",
    val activity: String = "",
    val storePhone: String = "",
    val socialReason: String = "",
    val commercialName: String = "",
    // Location
    val city: String = "",
    val zone: String = "",
    val address: String = "",
    val billingAddress: String = "",
    val gps: String = "",
    // Working hours
    val openingHour: String = "",
    val closingHour: String = "",
    // Legal ids
    val patenteNumber: String = "",
    val ice: String = "",
    val rc: String = "",
    val taxId: String = "",
)

@Immutable
data class BankState(
    val bank: String = "",
    val rib: String = "",
)

@Immutable
data class SegmentationState(
    val csp: String = "",
    val clientType: String = "",
    val potential: String = "",
    val zone: String = "",
)

// -------------------- Bottom Bar --------------------

@Composable
private fun BottomNavBar(
    activeStep: Int,
    stepsCount: Int,
    onPrev: () -> Unit,
    onNext: () -> Unit,
) {
    Surface(
        tonalElevation = 2.dp,
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (activeStep > 0) {
                OutlinedButton(
                    onClick = onPrev,
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.large,
                    contentPadding = PaddingValues(vertical = 14.dp)
                ) {
                    Icon(
                        Icons.AutoMirrored.Outlined.ArrowBack,
                        null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.previous), style = MaterialTheme.typography.labelLarge)
                }
            }

            Button(
                onClick = onNext,
                modifier = Modifier.weight(if (activeStep > 0) 1.5f else 1f),
                shape = MaterialTheme.shapes.large,
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                Text(
                    text = if (activeStep == stepsCount - 1)
                        stringResource(R.string.create_merchant)
                    else
                        stringResource(R.string.continue_next),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                if (activeStep < stepsCount - 1) {
                    Spacer(Modifier.width(8.dp))
                    Icon(
                        Icons.AutoMirrored.Outlined.ArrowForward,
                        null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

// -------------------- Vertical Stepper --------------------

@Composable
private fun VerticalStep(
    stepIndex: Int,
    title: String,
    isActive: Boolean,
    isCompleted: Boolean,
    isLast: Boolean,
    onStepClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val transition = updateTransition(targetState = isActive, label = "StepTransition")

    // Dynamic line color with a faster snap than the content expansion
    val lineColor by animateColorAsState(
        targetValue = if (isCompleted) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "lineColor"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min) // Essential for the vertical line to stretch
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onStepClick
            )
    ) {
        // --- Left Indicator Column ---
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(42.dp)
        ) {
            StepIndicator(
                index = stepIndex + 1,
                isActive = isActive,
                isCompleted = isCompleted
            )

            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .weight(1f)
                        .padding(vertical = 2.dp)
                        .background(lineColor, CircleShape)
                )
            }
        }

        Spacer(modifier = Modifier.width(20.dp))

        // --- Right Content Column ---
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = if (isLast) 24.dp else 36.dp)
        ) {
            // Title with a slight scale/color transition
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.SemiBold,
                color = animateColorAsState(
                    if (isActive) MaterialTheme.colorScheme.primary
                    else if (isCompleted) MaterialTheme.colorScheme.onSurface
                    else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                ).value,
                modifier = Modifier.padding(top = 4.dp)
            )

            // NEXT LEVEL ANIMATION: Spring-based expansion with Slide + Fade
            transition.AnimatedVisibility(
                visible = { it },
                enter = expandVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ) + fadeIn(tween(400, delayMillis = 100)),
                exit = shrinkVertically(tween(300)) + fadeOut(tween(200))
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    content = content
                )
            }
        }
    }
}

@Composable
private fun StepIndicator(
    index: Int,
    isActive: Boolean,
    isCompleted: Boolean
) {
    val containerColor by animateColorAsState(
        targetValue = when {
            isActive -> MaterialTheme.colorScheme.primary
            isCompleted -> MaterialTheme.colorScheme.primaryContainer
            else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        }, label = "container"
    )

    val contentColor by animateColorAsState(
        targetValue = when {
            isActive -> MaterialTheme.colorScheme.onPrimary
            isCompleted -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.onSurfaceVariant
        }, label = "content"
    )

    Surface(
        shape = CircleShape,
        color = containerColor,
        modifier = Modifier.size(32.dp), // More standard sizing
        border = if (isActive) null else BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (isCompleted) {
                Icon(
                    Icons.Rounded.Check, // Rounded icons usually look friendlier
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = contentColor
                )
            } else {
                Text(
                    text = index.toString(),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = contentColor
                )
            }
        }
    }
}

// -------------------- Reusable UI helpers --------------------

@Composable
private fun SectionTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier.padding(bottom = 4.dp),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(leadingIcon, null, Modifier.size(20.dp)) },
        placeholder = placeholder?.let { { Text(it) } },
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        shape = MaterialTheme.shapes.medium,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
        )
    )
}

@Composable
private fun UploadCard(
    label: String,
    subtitle: String? = null,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        tonalElevation = 1.dp,
        shadowElevation = 0.dp,
        color = MaterialTheme.colorScheme.surfaceVariant,
        onClick = onClick,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Box(
                    modifier = Modifier.size(44.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (!subtitle.isNullOrBlank()) {
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Outlined.UploadFile,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExposedDropdownField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            label = { Text(label) },
            leadingIcon = {
                if (leadingIcon != null) Icon(leadingIcon, null, Modifier.size(20.dp))
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelect(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

// -------------------- Forms (NO rows in Location group; kept consistent overall) --------------------

@Composable
private fun ContactForm(
    stepConfig: MerchantStepConfig,
    state: ContactState,
    onStateChange: (ContactState) -> Unit,
) {

    val section = stepConfig.section(MerchantSectionId.CONTACT_FIELDS)

    // If section disabled -> render nothing
    if (section == null) return

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

        if (section.fieldEnabled("firstName")) {
            AppTextField(
                value = state.firstName,
                onValueChange = { onStateChange(state.copy(firstName = it)) },
                label = stringResource(R.string.first_name),
                leadingIcon = Icons.Outlined.Person
            )
        }

        if (section.fieldEnabled("lastName")) {
            AppTextField(
                value = state.lastName,
                onValueChange = { onStateChange(state.copy(lastName = it)) },
                label = stringResource(R.string.last_name),
                leadingIcon = Icons.Outlined.PersonOutline
            )
        }

        if (section.fieldEnabled("phone")) {
            AppTextField(
                value = state.phone,
                onValueChange = { onStateChange(state.copy(phone = it)) },
                label = stringResource(R.string.phone_number),
                leadingIcon = Icons.Outlined.Phone,
                keyboardType = KeyboardType.Phone
            )
        }

        if (section.fieldEnabled("cin")) {
            AppTextField(
                value = state.cin,
                onValueChange = { onStateChange(state.copy(cin = it)) },
                label = stringResource(R.string.cin_optional),
                leadingIcon = Icons.Outlined.Badge
            )
        }

        if (section.fieldEnabled("cinPicture")) {
            UploadCard(
                label = stringResource(R.string.cin_picture),
                subtitle = stringResource(R.string.optional_formats_limit),
                icon = Icons.Outlined.CameraAlt
            )
        }
    }
}

@Composable
private fun MerchantForm(
    stepConfig: MerchantStepConfig,
    state: MerchantState,
    onStateChange: (MerchantState) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {

        // ---------------- BUSINESS PROFILE ----------------
        stepConfig.section(MerchantSectionId.BUSINESS_PROFILE)?.let { s ->
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SectionTitle(stringResource(R.string.business_profile))

                if (s.fieldEnabled("legalStatus")) {
                    SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                        SegmentedButton(
                            selected = state.legalStatus == "PHYSICAL",
                            onClick = { onStateChange(state.copy(legalStatus = "PHYSICAL")) },
                            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                            icon = {
                                SegmentedButtonDefaults.Icon(state.legalStatus == "PHYSICAL") {
                                    Icon(Icons.Outlined.Person, null, Modifier.size(18.dp))
                                }
                            }
                        ) { Text(stringResource(R.string.physical_person)) }

                        SegmentedButton(
                            selected = state.legalStatus == "MORAL",
                            onClick = { onStateChange(state.copy(legalStatus = "MORAL")) },
                            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                            icon = {
                                SegmentedButtonDefaults.Icon(state.legalStatus == "MORAL") {
                                    Icon(Icons.Outlined.Domain, null, Modifier.size(18.dp))
                                }
                            }
                        ) { Text(stringResource(R.string.legal_entity)) }
                    }
                }

                if (s.fieldEnabled("activity")) {
                    ExposedDropdownField(
                        label = stringResource(R.string.activity),
                        options = listOf(
                            "Restaurant", "Supermarket", "Sport Market", "Groceries",
                            "Library", "Phone Accessories", "Khadamt Service",
                            "Tashilat Service", "Services"
                        ),
                        selectedOption = state.activity,
                        onOptionSelect = { onStateChange(state.copy(activity = it)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                if (s.fieldEnabled("storePhone")) {
                    AppTextField(
                        value = state.storePhone,
                        onValueChange = { onStateChange(state.copy(storePhone = it)) },
                        label = stringResource(R.string.store_phone),
                        leadingIcon = Icons.Outlined.Phone,
                        keyboardType = KeyboardType.Phone
                    )
                }

                if (s.fieldEnabled("socialReason")) {
                    AppTextField(
                        value = state.socialReason,
                        onValueChange = { onStateChange(state.copy(socialReason = it)) },
                        label = stringResource(R.string.social_reason),
                        leadingIcon = Icons.Outlined.Description
                    )
                }

                if (s.fieldEnabled("commercialName")) {
                    AppTextField(
                        value = state.commercialName,
                        onValueChange = { onStateChange(state.copy(commercialName = it)) },
                        label = stringResource(R.string.commercial_name),
                        leadingIcon = Icons.Outlined.Storefront
                    )
                }
            }
        }

        // ---------------- LOCATION ----------------
        stepConfig.section(MerchantSectionId.LOCATION)?.let { s ->
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SectionTitle(stringResource(R.string.location))

                if (s.fieldEnabled("city")) {
                    AppTextField(
                        value = state.city,
                        onValueChange = { onStateChange(state.copy(city = it)) },
                        label = stringResource(R.string.city),
                        leadingIcon = Icons.Outlined.LocationCity
                    )
                }

                if (s.fieldEnabled("zone")) {
                    AppTextField(
                        value = state.zone,
                        onValueChange = { onStateChange(state.copy(zone = it)) },
                        label = stringResource(R.string.zone),
                        leadingIcon = Icons.Outlined.Map
                    )
                }

                if (s.fieldEnabled("address")) {
                    AppTextField(
                        value = state.address,
                        onValueChange = { onStateChange(state.copy(address = it)) },
                        label = stringResource(R.string.address),
                        leadingIcon = Icons.Outlined.Place
                    )
                }

                if (s.fieldEnabled("billingAddress")) {
                    AppTextField(
                        value = state.billingAddress,
                        onValueChange = { onStateChange(state.copy(billingAddress = it)) },
                        label = stringResource(R.string.billing_address),
                        leadingIcon = Icons.Outlined.ReceiptLong
                    )
                }

                if (s.fieldEnabled("gps")) {
                    AppTextField(
                        value = state.gps,
                        onValueChange = { onStateChange(state.copy(gps = it)) },
                        label = stringResource(R.string.gps_coordinates),
                        leadingIcon = Icons.Outlined.GpsFixed,
                        placeholder = stringResource(R.string.gps_placeholder),
                    )
                }
            }
        }

        // ---------------- WORKING HOURS ----------------
        stepConfig.section(MerchantSectionId.WORKING_HOURS)?.let { s ->
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SectionTitle(stringResource(R.string.working_hours))

                if (s.fieldEnabled("openingHour")) {
                    AppTextField(
                        value = state.openingHour,
                        onValueChange = { onStateChange(state.copy(openingHour = it)) },
                        label = stringResource(R.string.opening_time), // ✅ fixed label
                        leadingIcon = Icons.Outlined.Schedule,
                        placeholder = stringResource(R.string.opening_placeholder)
                    )
                }

                if (s.fieldEnabled("closingHour")) {
                    AppTextField(
                        value = state.closingHour,
                        onValueChange = { onStateChange(state.copy(closingHour = it)) },
                        label = stringResource(R.string.closing_time),
                        leadingIcon = Icons.Outlined.Schedule,
                        placeholder = stringResource(R.string.closing_placeholder)
                    )
                }
            }
        }

        // ---------------- LEGAL IDENTIFIERS ----------------
        stepConfig.section(MerchantSectionId.LEGAL_IDENTIFIERS)?.let { s ->
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SectionTitle(stringResource(R.string.legal_identifiers))

                if (s.fieldEnabled("patenteNumber")) {
                    AppTextField(
                        value = state.patenteNumber,
                        onValueChange = { onStateChange(state.copy(patenteNumber = it)) },
                        label = stringResource(R.string.patente_number),
                        leadingIcon = Icons.Outlined.ConfirmationNumber
                    )
                }

                if (s.fieldEnabled("ice")) {
                    AppTextField(
                        value = state.ice,
                        onValueChange = { onStateChange(state.copy(ice = it)) },
                        label = stringResource(R.string.ice),
                        leadingIcon = Icons.Outlined.Numbers
                    )
                }

                if (s.fieldEnabled("rc")) {
                    AppTextField(
                        value = state.rc,
                        onValueChange = { onStateChange(state.copy(rc = it)) },
                        label = stringResource(R.string.rc),
                        leadingIcon = Icons.Outlined.Badge
                    )
                }

                if (s.fieldEnabled("taxId")) {
                    AppTextField(
                        value = state.taxId,
                        onValueChange = { onStateChange(state.copy(taxId = it)) },
                        label = stringResource(R.string.tax_id),
                        leadingIcon = Icons.Outlined.AssignmentInd
                    )
                }
            }
        }

        // ---------------- PHOTOS ----------------
        stepConfig.section(MerchantSectionId.PHOTOS)?.let { s ->
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SectionTitle(stringResource(R.string.photos))

                if (s.fieldEnabled("exteriorPhoto")) {
                    UploadCard(label = stringResource(R.string.exterior_photo), icon = Icons.Outlined.Store)
                }
                if (s.fieldEnabled("interiorPhoto")) {
                    UploadCard(label = stringResource(R.string.interior_photo), icon = Icons.Outlined.MeetingRoom)
                }
            }
        }

        // ---------------- LEGAL DOCUMENTS ----------------
        stepConfig.section(MerchantSectionId.LEGAL_DOCUMENTS)?.let { s ->
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SectionTitle(stringResource(R.string.legal_documents))

                if (s.fieldEnabled("rcDocument")) {
                    UploadCard(label = stringResource(R.string.rc_document), icon = Icons.Outlined.Description)
                }
                if (s.fieldEnabled("iceDocument")) {
                    UploadCard(label = stringResource(R.string.ice_document), icon = Icons.Outlined.Description)
                }
                if (s.fieldEnabled("patenteDocument")) {
                    UploadCard(label = stringResource(R.string.patente_document), icon = Icons.Outlined.Description)
                }
            }
        }
    }
}


@Composable
private fun BankForm(
    stepConfig: MerchantStepConfig,
    state: BankState,
    onStateChange: (BankState) -> Unit,
) {
    val section = stepConfig.section(MerchantSectionId.BANK_FIELDS) ?: return

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        if (section.fieldEnabled("bank")) {
            AppTextField(
                value = state.bank,
                onValueChange = { onStateChange(state.copy(bank = it)) },
                label = stringResource(R.string.bank_name),
                leadingIcon = Icons.Outlined.AccountBalance
            )
        }

        if (section.fieldEnabled("rib")) {
            AppTextField(
                value = state.rib,
                onValueChange = { onStateChange(state.copy(rib = it)) },
                label = stringResource(R.string.rib),
                leadingIcon = Icons.Outlined.CreditCard,
                keyboardType = KeyboardType.Number
            )
        }

        if (section.fieldEnabled("ribDocument")) {
            UploadCard(
                label = stringResource(R.string.rib_document),
                icon = Icons.Outlined.Description
            )
        }
    }
}


@Composable
private fun SegmentationForm(
    stepConfig: MerchantStepConfig,
    state: SegmentationState,
    onStateChange: (SegmentationState) -> Unit,
) {
    val section = stepConfig.section(MerchantSectionId.SEGMENTATION_FIELDS) ?: return

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        if (section.fieldEnabled("csp")) {
            AppTextField(
                value = state.csp,
                onValueChange = { onStateChange(state.copy(csp = it)) },
                label = stringResource(R.string.csp),
                leadingIcon = Icons.Outlined.Groups
            )
        }

        if (section.fieldEnabled("clientType")) {
            AppTextField(
                value = state.clientType,
                onValueChange = { onStateChange(state.copy(clientType = it)) },
                label = stringResource(R.string.client_type),
                leadingIcon = Icons.Outlined.Badge
            )
        }

        if (section.fieldEnabled("potential")) {
            AppTextField(
                value = state.potential,
                onValueChange = { onStateChange(state.copy(potential = it)) },
                label = stringResource(R.string.potential),
                leadingIcon = Icons.Outlined.TrendingUp,
                keyboardType = KeyboardType.Number
            )
        }

        if (section.fieldEnabled("zone")) {
            AppTextField(
                value = state.zone,
                onValueChange = { onStateChange(state.copy(zone = it)) },
                label = stringResource(R.string.segmentation_zone),
                leadingIcon = Icons.Outlined.Map
            )
        }

        if (section.fieldEnabled("segmentationDocument")) {
            UploadCard(
                label = stringResource(R.string.segmentation_document),
                subtitle = stringResource(R.string.optional_file_formats_limit),
                icon = Icons.Outlined.Description
            )
        }
    }
}

