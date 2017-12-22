import sys

def twos_comp(val, bits):
    """compute the 2's complement of int value val"""
    if (val & (1 << (bits - 1))) != 0: # if sign bit is set e.g., 8bit: 128-255
        val = val - (1 << bits)        # compute negative value
    return val                         # return positive value as is

def convertToHex(bytesArray):
    hexString = "";
    for e in bytesArray:
        if e < 0:
            e = 256 + e

        hexString = hexString + "%02x" % e

    return "0x%s" % hexString

def convertToByteArray(hexString):
    byteArray = []
    for e in hexString:
        b = int(e.encode('hex'), 16) 
        byteArray.append(twos_comp(b, 8))

    return byteArray

def convertToString(byteArray):
    byteString = ""
    for e in byteArray:
        b = int(e.encode('hex'), 16) if isinstance(e, str) else e
        b = b & 127
        byteString = byteString + "%s" % chr(b)
        if (b & 0x80) > 0:
            break

    return byteString

key = [16, -91, -96, 114, 116, 30, 117, 115, 101, 114, 78, 97, 109, -27]
print "key_raw: %s" % key
print "key_text: %s" % convertToString(key)
print "key_hex: %s" % convertToHex(key)

value = "0093be84a06d617070696ee7b882a06d61707065642d6e616de592a0757365724e616de5a07374617475f3b382".decode("hex")
print "value_text: %s" % convertToString(value)

value = "10a5a067691e62795f7374617475735f636f6e74656ef4".decode("hex")
print "value_text: %s" % convertToString(value)

value = "10a5a067691e62795f706f7374735f6372656174656441f4".decode("hex")
print "value_text: %s" % convertToString(value)

value = "10a5a067691e62795f757365725f757365724e616de5".decode("hex")
print "value_text: %s" % convertToString(value)

key = [99, 111, 110, 102, 105, 103, 117, 114, 97, 116, 105, 111, 110]
print "key_text: %s" % convertToString(key)
print "key_hex: %s" % convertToHex(key)

key = [0, 0, 0, 0, 0, 0, 0, -128]
print "key_text: %s" % convertToString(key)
print "key_hex: %s" % convertToHex(key)

value = "048d13440880ff".decode("hex")
print "value_text: %s" % convertToString(value)

value = [36, 4, -115, 19, 68, 8, -128, -1]
print "key_text: %s" % convertToString(value)
print "key_hex: %s" % convertToHex(value)

value = [80, -64, -96, 116, 101, 115, 116, 85, 115, 101, 114, -80, 19, 68, 12, -128, 80, -32, -96, 70, 111, 111, -80, 19, 68, 16, -128, 81, -128, -96, 66, 97, 114, -80, 19, 68, 20, -128]
print "key_text: %s" % convertToString(value)
print "key_hex: %s" % convertToHex(value)

value = "009480".decode("hex")
print "value_text: %s" % convertToByteArray(value)
print 

value_input = "a067691e62795f757365725f757365724e616de5013080"
value = value_input.decode("hex")
print "value_input: %s" % value_input
print "value_array: %s" % convertToByteArray(value)
print "value_text: %s" % convertToString(value)
print

key = [0, 0, 0, 0, 0, 0, 2, 13]
print "key_text: %s" % convertToString(key)
print "key_hex: %s" % convertToHex(key)
print

value_input = "0093be84a06d617070696ee7b882a06d61707065642d6e616de592a0757365724e616de5a07374617475f3b382"
value = value_input.decode("hex")
print "value_input: %s" % value_input
print "value_text: %s" % convertToString(value)
print

value_input = "a067691e62795f757365725f757365724e616de501"
value = value_input.decode("hex")
print "value_input: %s" % value_input
print "value_array: %s" % convertToByteArray(value)
print "value_text: %s" % convertToString(value)
print

value = "a0766c1e757365f20480".decode("hex")
print "value_text: %s" % convertToString(value)
print

key = [48, 0, 0, 0, 0, 0, 0, -128]
print "key_hex: %s" % convertToHex(key)
print


value_input = "a07465737455736572b00c86"
value = value_input.decode("hex")
print "value_input: %s" % value_input
print "value_array: %s" % convertToByteArray(value)
print "value_text: %s" % convertToString(value)
print


value_input = "a07465737455736572b00c86"
value = value_input.decode("hex")
print "value_input: %s" % value_input
print "value_array: %s" % convertToByteArray(value)
print "value_text: %s" % convertToString(value)
print


value_input = "a0466f6fb01086"
value = value_input.decode("hex")
print "value_input: %s" % value_input
print "value_array: %s" % convertToByteArray(value)
print "value_text: %s" % convertToString(value)
print


value_input = "a0426172b01486"
value = value_input.decode("hex")
print "value_input: %s" % value_input
print "value_array: %s" % convertToByteArray(value)
print "value_text: %s" % convertToString(value)
print


q = [83,69,76,69,67,84,32,32,96,99,111,117,112,111,110,95,117,115,101,114,115,96,46,42,32,70,82,79,77,32,96,99,111,117,112,111,110,95,117,115,101,114,115,96,32,87,72,69,82,69,32,96,99,111,117,112,111,110,95,117,115,101,114,115,96,46,96,117,115,101,114,95,99,111,117,112,111,110,95,105,100,101,110,116,105,102,121,95,107,101,121,96,32,61,32,39,55,101,55,100,98,48,98,100,102,55,100,99,48,53,99,54,51,101,101,55,49,54,98,48,56,49,97,48,101,57,99,57,39,32,76,73,77,73,84,32,49]
print "q_text: %s" % convertToString(q)

q = [83,69,76,69,67,84,32,99,111,117,110,116,40,42,41,32,65,83,32,99,111,117,110,116,44,32,109,97,120,40,117,112,100,97,116,101,100,95,97,116,41,32,65,83,32,109,97,120,32,70,82,79,77,32,102,105,110,99,95,115,116,111,114,101,95,112,114,111,100,117,99,116,105,111,110,46,117,115,101,114,115,32,76,73,77,73,84,32,49]
print "q_text: %s" % convertToString(q)

q = [83,69,76,69,67,84,32,67,79,85,78,84,40,42,41,32,70,82,79,77,32,96,117,115,101,114,115,96,32,87,72,69,82,69,32,96,117,115,101,114,115,96,46,96,116,121,112,101,96,32,73,78,32,40,39,82,101,103,105,115,116,101,114,101,100,85,115,101,114,39,41]
print "q_text: %s" % convertToString(q)

q = [83,69,76,69,67,84,32,32,96,99,111,117,112,111,110,95,117,115,101,114,115,96,46,42,32,70,82,79,77,32,96,99,111,117,112,111,110,95,117,115,101,114,115,96,32,87,72,69,82,69,32,96,99,111,117,112,111,110,95,117,115,101,114,115,96,46,96,117,115,101,114,95,99,111,117,112,111,110,95,105,100,101,110,116,105,102,121,95,107,101,121,96,32,61,32,39,49,51,101,49,48,50,48,57,57,102,99,48,53,52,49,102,57,97,55,49,56,97,101,100,97,97,56,100,98,49,53,56,39,32,76,73,77,73,84,32,49]
print "q_text: %s" % convertToString(q)

q = [115,101,108,101,99,116,32,42,32,10,102,114,111,109,32,32,32,112,114,101,115,99,114,105,112,116,105,111,110,115,44,32,115,117,98,115,99,114,105,112,116,105,111,110,95,108,105,110,101,95,105,116,101,109,115,44,32,115,117,98,115,99,114,105,112,116,105,111,110,95,111,114,100,101,114,95,100,101,116,97,105,108,115,10,119,104,101,114,101,32,32,112,114,101,115,99,114,105,112,116,105,111,110,115,46,108,105,110,101,95,105,116,101,109,95,105,100,32,61,32,115,117,98,115,99,114,105,112,116,105,111,110,95,108,105,110,101,95,105,116,101,109,115,46,105,100,10,97,110,100,32,32,32,32,115,117,98,115,99,114,105,112,116,105,111,110,95,108,105,110,101,95,105,116,101,109,115,46,111,114,100,101,114,95,100,101,116,97,105,108,95,105,100,32,61,32,115,117,98,115,99,114,105,112,116,105,111,110,95,111,114,100,101,114,95,100,101,116,97,105,108,115,46,105,100,10,97,110,100,32,32,32,32,99,111,109,112,111,115,105,116,105,111,110,32,108,105,107,101,32,39,37,227,130,180,227,131,158,227,130,170,227,130,164,227,131,171,229,144,171,230,156,137,233,163,159,229,147,129,37,39,32,111,114,32,99,111,109,112,111,115,105,116,105,111,110,32,108,105,107,101,32,39,37,83,101,115,97,109,101,32,111,105,108,45,99,111,110,116,97,105,110,105,110,103,32,102,111,111,100,37,39]
print "q_text: %s" % convertToString(q)

