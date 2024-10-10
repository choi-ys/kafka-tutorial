dependencies {
    implementation(project(":core:message"))
//    implementation(project(":support:web"))
    implementation("org.springframework.boot:spring-boot-starter")

    implementation(project(":support:swagger"))

    implementation("org.springframework.kafka:spring-kafka")

    testImplementation("org.springframework.kafka:spring-kafka-test")
}
