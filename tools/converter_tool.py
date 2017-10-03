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
    ba = byteArray[1:]
    byteString = ""
    for e in ba:
        b = int(e.encode('hex'), 16) if isinstance(e, str) else e
        b = b & 127
        byteString = byteString + "%s" % chr(b)
        if (b & 0x80) > 0:
            break

    return byteString

key = [16, -91, -96, 114, 116, 30, 117, 115, 101, 114, 78, 97, 109, -27]
print "key: %s" % convertToString(key)
print "key: %s" % convertToHex(key)

