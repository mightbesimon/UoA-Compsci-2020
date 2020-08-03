from xor_hex import *

def split(hex_plaintext):
	l0, r0 = hex_plaintext[:len(hex_plaintext)//2], hex_plaintext[len(hex_plaintext)//2:]
	return (l0, r0)

def feistel(c0, key):
	l0, r0 = c0
	l1 = r0
	r1 = xor_hex(l0, func(r0, key))
	return (l1, r1)

def encrypt(plaintext, keys):
	c = [split(ascii2hex(plaintext))]
	for idx in range(len(keys)):
		c.append(feistel(c[idx], keys[idx]))
	ln, rn = c[-1]
	return rn+ln

def decrypt(ciphertext, keys):
	p, keys = [split(ciphertext)], keys[::-1]
	for idx in range(len(keys)):
		p.append(feistel(p[idx], keys[idx]))
	ln, rn = p[-1]
	return hex2ascii(rn+ln)

def func(arg0, arg1):
	hex_sum = hex(int(arg0, 16) + int(arg1, 16)).upper()[2:]
	hex_sum = '0'*len(arg0) + hex_sum
	return hex_sum[len(hex_sum)-len(arg0):]

if __name__ == '__main__':
	p = 'Always blow on the pie.'
	k = ['DB26', '106A', '1111', 'ABCD']
	c = encrypt(p, k)
	print(c)
	print(decrypt(c, k))
	# print(k)

