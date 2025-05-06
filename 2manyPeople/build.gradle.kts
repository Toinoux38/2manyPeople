plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "com.citybuilder"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

javafx {
    version = "22"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.google.dagger:dagger:2.45")
    annotationProcessor("com.google.dagger:dagger-compiler:2.45")
}


application {
    mainClass.set("com.citybuilder.Main")
}

tasks.test {
    useJUnitPlatform()
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from("src/main/resources")
}