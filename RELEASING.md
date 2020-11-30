# Releasing

The `main` branch is the base for regular releases.

## Cutting the release

Travis CI builds and publishes releases to Bintray.

### Requires contributor access

- Check the [draft release notes](https://github.com/lightbend/sbt-bill-of-materials/releases) to see if everything is there
- Update the [draft release](https://github.com/lightbend/sbt-bill-of-materials/releases) with the next tag version (eg. `v1.0.0`), title and release description
- Check that Travis CI release build has executed successfully (Travis will start a [CI build](https://travis-ci.com/github/lightbend/sbt-bill-of-materials/builds)
  for the new tag and publish artifacts to Bintray)
