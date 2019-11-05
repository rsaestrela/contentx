package contentx.core

enum class CxConstant(val v: String) {
    ID("_id"),
    NAME("name"),
    PARENT("parent"),
    ROOT("root");

    companion object {
        fun lookup(v: String): CxConstant {
            return values().first { e -> e.v == v }
        }
    }
}