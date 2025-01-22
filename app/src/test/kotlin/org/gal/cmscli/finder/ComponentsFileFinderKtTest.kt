package org.gal.cmscli.finder

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File

class ComponentsFileFinderKtTest {

    @Test
    fun `find smart edit component file should find file`() {
        val file = findSmartEditComponentsFile()
        file?.also {
            assertThat(it).isFile()
            assertThat(it.absolutePath).contains(DEFAULT_COMPONENT_FILE_NAME)
            File(CACHE_FILE_PATH).delete() // Cleanup cache file
        }
    }
}