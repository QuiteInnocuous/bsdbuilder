import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    application
}

group = "com.quiteinnocuous"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/com.squareup/kotlinpoet
    implementation("com.squareup:kotlinpoet:1.12.0")
    // https://mavenlibs.com/maven/dependency/org.openjax.xml/sax
    //mplementation("org.openjax.xml:sax:0.9.4")
    implementation("sax:sax:2.0.1")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-xml
    //implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.11.1")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")

//    <groupId>com.fasterxml.jackson.dataformat</groupId>
//    <artifactId>jackson-dataformat-xml</artifactId>
//    <version>2.11.1</version>


    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}