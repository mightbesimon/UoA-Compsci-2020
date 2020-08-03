'''
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
	b1, b2 = bool(int(b1)), bool(int(b2))
	return (b1 or b2) and not(b1 and b2)

def xor_hex(h1, h2):
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
'''

from xor_hex import *


if __name__ == '__main__':
	c0 = '5734D8A2A576F3D24D5D21795E3D9F802EAF04CC572030ACAD27'
	c1 = '5332DBA9EC7AFD80480E737B5C369F962DA315DB40612DA9B427'
	c2 = '4232C3A2A567F2C14D4B723A50359F9433A7189E41392CB3AD27'
	c3 = '5034DAE7E27BF5C409416F7F1F3ACCD335AD0E9E57292AB2AD27'
	c4 = '4A34C1E7E434E9C947496D7F1F31D68125E215C9412431A5BD27'
	c5 = '572BDCA4FC34FCCF464A21734D21D68720B604CD04243CA5AA27'
	c6 = '543AC7A6F67BF6D3094F737F1F35D08161B114D057292CAEBC27'

	t0 = 'Some birds can somersault.'
	p0 = '536F6D652062697264732063616E20736F6D65727361756C742E'
	key = '045BB5C785149AA0292E011A3F53BFF341C261BE244145C0D909'
	# print('0 12345678901234567890123456')
	# print('0 xxxx xxxxx xxx xxxxxxxxxx.')
	# print('0 xxxE BxRDS CAx SOMxRSAxxx.')
	# print('0 SOME BIRDS CAN SOMERSAULT.')
	# print(1, xpad(c0, c1))
	# print(2, xpad(c0, c2))
	# print(3, xpad(c0, c3))
	# print(4, xpad(c0, c4))
	# print(5, xpad(c0, c5))
	# print(6, xpad(c0, c6))
	# print(xor_hex(c0, p0))
	# key = xor_hex(ascii2hex('some birds can somersault.'), c0)
	print(key)
	print(0, xpad(c0, key))
	print(1, xpad(c1, key))
	print(2, xpad(c2, key))
	print(3, xpad(c3, key))
	print(4, xpad(c4, key))
	print(5, xpad(c5, key))
	print(6, xpad(c6, key))


