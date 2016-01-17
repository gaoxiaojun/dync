cmake_minimum_required(VERSION 2.8)
project(adlc)
include_directories(..)

set(ADLC_SRC
adlparse.cpp
archDesc.cpp
arena.cpp
dfa.cpp
dict2.cpp
filebuff.cpp
forms.cpp
formsopt.cpp
formssel.cpp
main.cpp
output_c.cpp
output_h.cpp
../opto/opcodes.cpp
)

add_executable(adlc ${ADLC_SRC})
