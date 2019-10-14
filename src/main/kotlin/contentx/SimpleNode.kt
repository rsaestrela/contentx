package contentx

import java.util.*

class SimpleNode : Node {

    override fun id(): String {
        return UUID.randomUUID().toString()
    }

}