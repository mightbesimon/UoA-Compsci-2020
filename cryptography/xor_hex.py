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


def hex2bin(h):
	table = str.maketrans(hex2bin_table)
	return h.translate(table)

def bin2hex(b):
	bin2hex_table = { v: k for k, v in hex2bin_table.items() }
	b_split = [ b[i*4:i*4+4] for i in range(len(b)//4) ]
	return ''.join([bin2hex_table[b_4] for b_4 in b_split ])
	# return hex(int(b, 2)).upper()[2:]


def xor(b1, b2):
	return int(b1) ^ int(b2)

def xor_hex(h1, h2):
	if len(h1) != len(h2): raise ValueError
	h1, h2 = hex2bin(h1), hex2bin(h2)
	b_xor = ''.join(
		[ str(int(xor(h1[idx], h2[idx]))) 
		for idx in range(max(len(h1), len(h2))) 
		])
	return bin2hex(b_xor)


def hex2ascii(h):
	h_split = [ h[i*2:i*2+2] for i in range(len(h)//2) ]
	d_split = [ int(h_2, 16) for h_2 in h_split ]
	return ''.join([ chr(d) if d else '?' for d in d_split ])

def ascii2hex(a):
	return ''.join([ hex(ord(c)).upper()[2:] for c in a ])


def filter_accpeted(string):
	accepted = '\
0123456789\
ABCDEFGHIJKLMNOPQRSTUVWXYZ\
abcdefghijklmnopqrstuvwxyz\
;: ,.!?()'
	return ''.join([ c if c in accepted else ' ' for c in string ])

def xpad(c1, c2):
	return filter_accpeted(hex2ascii(xor_hex(c1, c2)))


