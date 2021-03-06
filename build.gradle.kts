plugins {
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.serialization") version "1.6.10"
	id("java")
	id("com.github.johnrengelman.shadow") version "6.0.0"
	id("maven-publish")
	id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
	id("signing")
}

group = "io.github.uinnn"
version = "1.9.5"

repositories {
	mavenLocal()
	mavenCentral()
}

dependencies {
	api("io.github.uinnn:walk-server:2.4.0")
	api(kotlin("stdlib-jdk8"))
	compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
}

nexusPublishing {
	repositories {
		sonatype {
			nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
			snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
		}
	}
}

tasks {
	publishing {
		repositories {
			maven {
				url = uri("https://repo.maven.apache.org/maven2/")
			}
		}
		
		publications {
			create<MavenPublication>("maven") {
				from(project.components["kotlin"])
				
				val sourcesJar by creating(Jar::class) {
					archiveClassifier.set("sources")
					from(sourceSets.main.get().allSource)
				}
				
				val javadocJar by creating(Jar::class) {
					dependsOn.add(javadoc)
					archiveClassifier.set("javadoc")
					from(javadoc)
				}
				
				setArtifacts(listOf(sourcesJar, javadocJar, jar))
				
				groupId = "io.github.uinnn"
				artifactId = "interface-framework"
				version = project.version.toString()
				
				pom {
					name.set("interface-framework")
					description.set("a complex and complete interface (inventory) framework write in kotlin for spigot use!")
					url.set("https://github.com/uinnn/interface-framework")
					developers {
						developer {
							id.set("uinnn")
							name.set("Uin Carrara")
							email.set("uin.carrara@gmail.com")
						}
					}
					licenses {
						license {
							name.set("MIT Licenses")
						}
					}
					scm {
						url.set("https://github.com/uinnn/interface-framework/tree/master/src")
					}
				}
			}
		}
	}
	
	signing {
		sign(publishing.publications["maven"])
	}
	
	compileKotlin {
		targetCompatibility = "1.8"
		sourceCompatibility = "1.8"
		
		kotlinOptions.freeCompilerArgs +=
			"-Xopt-in=kotlin.time.ExperimentalTime," +
				"kotlin.ExperimentalStdlibApi," +
				"kotlinx.coroutines.DelicateCoroutinesApi," +
				"kotlinx.coroutines.ExperimentalCoroutinesApi," +
				"kotlinx.coroutines.InternalCoroutinesApi"
	}
	
	shadowJar {
		destinationDir = file("C:\\Users\\Cliente\\Minecraft\\Local\\plugins")
		archiveName = "${project.name}.jar"
		baseName = project.name
		version = project.version.toString()
		relocate("net.minecraft.server", "net.minecraft.server.v1_8_R3")
		relocate("org.bukkit.craftbukkit", "org.bukkit.craftbukkit.v1_8_R3")
	}
}
