# Releasing

The `main` branch is the base for regular releases.

## Cutting the release

GitHub workflows publish releases to the [Sonatype Central Portal](https://central.sonatype.com/).

The `SONATYPE_USERNAME` / `SONATYPE_PASSWORD` GitHub secrets must be a Central Portal user token generated at https://central.sonatype.com/account (the account must own the `com.lightbend` namespace). 

### Requires contributor access

- Check the [draft release notes](https://github.com/lightbend/sbt-bill-of-materials/releases) to see if everything is there
- Update the [draft release](https://github.com/lightbend/sbt-bill-of-materials/releases) with the next tag version (eg. `v1.0.0`), title and release description
- Check that the GitHub "Release" workflow executes successfully [Actions](https://github.com/lightbend/sbt-bill-of-materials/actions)
