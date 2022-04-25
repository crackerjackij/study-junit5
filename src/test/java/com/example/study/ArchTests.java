package com.example.study;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

public class ArchTests {

    @Test
    void packageDepedecyTests(){
        JavaClasses classes = new ClassFileImporter().importPackages("com.example");

        ArchRule domainPackageRule = classes().that().resideInAnyPackage("..domain..")
                .should().onlyBeAccessed().byClassesThat().resideInAnyPackage("..study..", "..member..", "..domain..");

        domainPackageRule.check(classes);

        ArchRule memberPackageRule = noClasses().that().resideInAnyPackage("..domain..")
                .should().accessClassesThat().resideInAPackage("..member..");

        memberPackageRule.check(classes);

        ArchRule studyPackageRule = noClasses().that().resideOutsideOfPackage("..study..")
                .should().accessClassesThat().resideInAPackage("..study..");

        studyPackageRule.check(classes);

        ArchRule freeOfCycles = slices().matching("..example.(*)..")
                .should().beFreeOfCycles();

        freeOfCycles.check(classes);
    }
}
