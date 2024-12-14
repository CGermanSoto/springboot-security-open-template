# Internationalization (i18n) Refactor Plan

## Current Implementation Analysis
1. Messages are stored in:
   - [src/main/resources/locale/message.properties](/src/main/resources/locale/message.properties)
   - [src/main/resources/locale/message_es.properties](src/main/resources/locale/message_es.properties)
   - [src/main/resources/validation/validation.properties](src/main/resources/validation/validation.properties)
   - [src/main/resources/validation/validation_es.properties](src/main/resources/validation/validation_es.properties)

2. Message resolution is handled by:
   - [`MessageUtilComponent`](src/main/java/com/spacecodee/springbootsecurityopentemplate/language/MessageUtilComponent.java)
   - [`MessageParameterUtil`](src/main/java/com/spacecodee/springbootsecurityopentemplate/exceptions/util/MessageParameterUtil.java)
   - [`ValidationConstants`](src/main/java/com/spacecodee/springbootsecurityopentemplate/constants/ValidationConstants.java)

## Proposed Changes

### Phase 1: Message Templates Standardization
1. Update message properties to use placeholder notation:
   - Convert static messages to parameterized messages
   - Use numbered placeholders {0}, {1}, etc.
   - Add comments to document parameters

### Phase 2: Message Utility Enhancement
1. Create new MessageFormat wrapper methods
2. Add parameter validation
3. Implement type-safe message building

### Phase 3: Controller Integration
1. Update AbstractController response methods
2. Create message parameter builders
3. Implement in authentication responses

### Phase 4: Validation Messages
1. Update validation annotations
2. Create custom validators with parameterized messages
3. Implement parameter substitution in constraint violations

## Implementation Order
1. Start with authentication messages (login, registration)
2. Move to validation messages
3. Update error messages
4. Finally, update success messages