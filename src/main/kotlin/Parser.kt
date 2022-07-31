package dev.sal.kidcat

/**
 * Find the currently running Package
 */
internal fun String.currentPackage() = this.let { line ->
  """.*TaskRecord.*A[= ](?<package>[^ ^}]*)"""
    .toRegex()
    .find(line)
    ?.groups
    ?.get("package")
    ?.let { Package(it.value) }
}
