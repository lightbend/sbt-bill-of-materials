# Releasing

The `main` branch is the base for regular releases.

## Cutting the release

GitHub workflows publish releases to Sonatype.

### Requires contributor access

- Check the [draft release notes](https://github.com/lightbend/sbt-bill-of-materials/releases) to see if everything is there
- Update the [draft release](https://github.com/lightbend/sbt-bill-of-materials/releases) with the next tag version (eg. `v1.0.0`), title and release description
- Check that the GitHub "Release" workflow executes successfully [Actions](https://github.com/lightbend/sbt-bill-of-materials/actions)
