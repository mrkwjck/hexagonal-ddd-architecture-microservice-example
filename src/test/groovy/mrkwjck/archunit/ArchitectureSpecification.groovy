package mrkwjck.archunit

import com.tngtech.archunit.base.DescribedPredicate
import com.tngtech.archunit.core.domain.JavaClass
import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import mrkwjck.HexagonalDddExampleApplication
import spock.lang.Specification

class ArchitectureSpecification extends Specification {

    static final String PACKAGE_INFRASTRUCTURE = "mrkwjck.infrastructure.."
    static final String PACKAGE_APPLICATION = "mrkwjck.application.."
    static final String PACKAGE_DOMAIN = "mrkwjck.domain.."
    static final String[] ALLOWED_PACKAGES = [
            PACKAGE_INFRASTRUCTURE,
            PACKAGE_DOMAIN,
            PACKAGE_APPLICATION
    ]

    static final JavaClasses CLASSES = new ClassFileImporter()
            .withImportOption(new ImportOption.DoNotIncludeTests())
            .importPackages(HexagonalDddExampleApplication.class.getPackageName())
            .that(isNotMainApplicationClassPredicate())

    private static DescribedPredicate<JavaClass> isNotMainApplicationClassPredicate() {
        return new DescribedPredicate<JavaClass>("is not main application class") {
            @Override
            boolean test(JavaClass javaClass) {
                return javaClass.getName() != HexagonalDddExampleApplication.class.getName()
            }
        }
    }

}
