import org.springframework.boot.gradle.tasks.bundling.BootJar

//tasks.withType<BootJar> { enabled = false }
tasks.withType<Jar> { enabled = true }

tasks.named<BootJar>("bootJar") {
    enabled = false
}

dependencies {
}
