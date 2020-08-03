hex2bin_table = {
	'0': '0000',
	'1': '0001',
	'2': '0010',
	'3': '0011',
	'4': '0100',
	'5': '0101',
	'6': '0110',
	'7': '0111',
	'8': '1000',
	'9': '1001',
	'A': '1010',
	'B': '1011',
	'C': '1100',
	'D': '1101',
	'E': '1110',
	'F': '1111',
}

FILENAME = 'present_hex.txt'

def lsb_hex(hex_rgb):
	return ''.join([ hex2bin_table[h][-1] for h in hex_rgb[1::2] ])

def split2bytes(bin_str):
	return [bin_str[:8]] + split2bytes(bin_str[8:]) if len(bin_str)>=8 else []

def rp(thing):
	print(thing)
	return thing

if __name__ == '__main__':
	with open(FILENAME, 'r') as file: content = file.read().split()
	lsb = ''.join([lsb_hex(rgb) for rgb in content])

	len_bin = lsb[:32]
	len_p = int(len_bin[::-1], 2)

	msg_byte = split2bytes(lsb[32:])
	msg = ''.join([chr(int(byte[::-1], 2)) for byte in msg_byte])
	print(msg)
	print(len(msg)-len_p)

