dependencies {
    implementation(project(":support:swagger"))
    implementation(project(":support:web"))
    implementation("org.springframework.kafka:spring-kafka")

    testImplementation("org.springframework.kafka:spring-kafka-test")
}
