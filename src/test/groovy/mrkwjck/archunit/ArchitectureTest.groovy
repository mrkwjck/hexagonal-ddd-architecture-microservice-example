package mrkwjck.archunit


import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import static com.tngtech.archunit.library.Architectures.layeredArchitecture

class ArchitectureTest extends ArchitectureSpecification {

    private static final String LAYER_INFRASTRUCTURE = 'Infrastructure'
    private static final String LAYER_APPLICATION = 'Application'
    private static final String LAYER_DOMAIN = 'Domain'

    def "should respect layers dependencies"() {
        expect:
        layeredArchitecture()
                .consideringOnlyDependenciesInLayers()
                .optionalLayer(LAYER_APPLICATION).definedBy(PACKAGE_APPLICATION)
                .optionalLayer(LAYER_DOMAIN).definedBy(PACKAGE_DOMAIN)
                .optionalLayer(LAYER_INFRASTRUCTURE).definedBy(PACKAGE_INFRASTRUCTURE)
                .whereLayer(LAYER_DOMAIN).mayOnlyBeAccessedByLayers(LAYER_APPLICATION, LAYER_INFRASTRUCTURE)
                .whereLayer(LAYER_INFRASTRUCTURE).mayOnlyBeAccessedByLayers(LAYER_APPLICATION)
                .check(CLASSES)
    }

    def "should have classes placed only in allowed packages"() {
        expect:
        noClasses().should().resideOutsideOfPackages(ALLOWED_PACKAGES).check(CLASSES)
    }

}
