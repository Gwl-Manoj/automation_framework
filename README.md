# Automation Framework

Reusable Selenium + TestNG UI automation framework, published as a Maven dependency
from this GitHub repository.

- **Group ID:** `com.automation`
- **Artifact ID:** `AutomationFramework`
- **Version:** `1.0.0`
- **GitHub repo:** https://github.com/Gwl-Manoj/automation_framework

## How it is published

The framework jar is published to a Maven repository that lives in the
**`maven-repo`** branch of this repository. The GitHub Actions workflow
(`.github/workflows/publish.yml`) builds the project on a version tag (`v*`) or
manual dispatch and force-pushes the artifacts to that branch.

Consumers reference the repository directly from GitHub — no local install and no
authentication required (for a public repository).

## Use it as a dependency

Add the repository and the dependency to your project's `pom.xml`:

```xml
<repositories>
    <repository>
        <id>automation-framework-github</id>
        <url>https://raw.githubusercontent.com/Gwl-Manoj/automation_framework/maven-repo/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.automation</groupId>
        <artifactId>AutomationFramework</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

Then write your own tests by extending `com.automation.base.BaseTest` and
`com.automation.pages.BasePage`. Place your `config/config.properties` on the
classpath (or pass `-Dconfig.file=/path/to/config.properties`).

## Publish a new version

1. Update the `<version>` in `pom.xml` (e.g. `1.0.1`).
2. Commit and push the change.
3. Create and push a tag: `git tag v1.0.1 && git push origin v1.0.1`.
4. The workflow publishes the artifacts to the `maven-repo` branch automatically.

To publish manually, run the **Publish Maven Repository to GitHub** workflow from
the Actions tab.

## Local build

```bash
mvn clean install      # install into local ~/.m2
mvn clean deploy       # build + stage into target/maven-repo-staging
mvn test -DskipTests=false   # run the example tests