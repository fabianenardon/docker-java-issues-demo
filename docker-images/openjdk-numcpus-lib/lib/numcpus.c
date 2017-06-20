#include <stdlib.h>
#include <unistd.h>

int JVM_ActiveProcessorCount(void) {
    char* val = getenv("_NUM_CPUS");
    return val != NULL ? atoi(val) : sysconf(_SC_NPROCESSORS_ONLN);
}