;
; Initialization
;
		.ORIG	x3000
		LD		R6, EMPTY		; R6 is the stack pointer
		LD		R5, PTR			; R5 is pointer to characters
		AND		R0,	R0,	#0
		ADD		R0,	R0,	#10		; Print a new line
		OUT
		
		LDR		R3, R5, #0		; R3 gets the first character
		NOT		R3, R3
		ADD		R3, R3, #1
		AND		R4, R4, #0		; R4 stores the operation type
		LD		R0, PLUS		; '+'
		ADD		R0, R0, R3
		BRz		O_PLUS
		LD		R0, SOR			; '|'
		ADD		R0, R0, R3
		BRz		O_OR
		LD		R0, SAND		; '&'
		ADD		R0, R0, R3
		BRz		O_AND
		LD		R0, MINUS		; '-'
		ADD		R0, R0, R3
		BRz		O_MINUS
		LD		R0, MULT		; '*'
		ADD		R0, R0, R3
		BRz		O_MULT
		LD		R0, POW			; '^'
		ADD		R0, R0, R3
		BRz		O_POW
O_PLUS	ADD		R4, R4, #0
		BRnzp	O_end
O_OR	ADD		R4, R4, #1
		BRnzp	O_end
O_AND	ADD		R4, R4, #2
		BRnzp	O_end	
O_MINUS	ADD		R4, R4, #3
		BRnzp	O_end
O_MULT	ADD		R4, R4, #4
		BRnzp	O_end
O_POW	ADD		R4, R4, #5
		BRnzp	O_end
O_end	ADD		R5, R5, #1

;	
REDO	LDR		R3, R5, #0		; R3 gets character
;
; Test character for end of file
;		
		ADD		R1, R3, #-10	; Test for end of line (ASCII xA)
		BRz		EXIT			; If done, quit
		LD		R1, ZERO
		ADD		R3, R3, R1		; Get the decimal value from ASCII
		JSR		CONV
		ADD		R5, R5, #1
		AND		R1, R5, #1		; check odd/even
		BRp		EVEN
		ADD		R2, R3, #0		; Save the first operand to R2; The second operand is at R3

		ADD		R0, R4, #0
		BRz		P_PLUS
		ADD		R0, R4, #-1
		BRz		P_SOR
		ADD		R0, R4, #-2
		BRz		P_SAND
		ADD		R0, R4, #-3
		BRz		P_MINUS
		ADD		R0, R4, #-4
		BRz		P_MULT
		ADD		R0, R4, #-5
		BRz		P_POW
P_PLUS	LD		R0, PLUS		; '+'
		BRnzp	P_end
P_SOR	LD		R0, SOR			; '|'
		BRnzp	P_end
P_SAND	LD		R0, SAND		; '&'
		BRnzp	P_end
P_MINUS	LD		R0, MINUS		; '-'
		BRnzp	P_end
P_MULT	LD		R0, MULT		; '*'
		BRnzp	P_end
P_POW	LD		R0, POW			; '^'
		BRnzp	P_end		
P_end	OUT
		BRnzp	REDO
EVEN	LD		R0, EQUAL		; '='
		OUT
		
		
; Start calculation
		ADD		R0, R4, #0
		BRz		C_PLUS
		ADD		R0, R4, #-1
		BRz		C_SOR
		ADD		R0, R4, #-2
		BRz		C_SAND
		ADD		R0, R4, #-3
		BRz		C_MINUS
		ADD		R0, R4, #-4
		BRz		C_MULT
		ADD		R0, R4, #-5
		BRz		C_POW
C_PLUS	JSR		MyADD			; '+'
		BRnzp	C_end
C_SOR	JSR		MyOR			; '|'
		BRnzp	C_end
C_SAND	JSR		MyAND			; '&'
		BRnzp	C_end
C_MINUS	JSR		MySUB			; '-'
		BRnzp	C_end
C_MULT	JSR		MyMULT			; '*'
		BRnzp	C_end
C_POW	JSR		MyPOW			; '^'
		BRnzp	C_end		
;
C_end	JSR		CONV
		AND		R0,	R0,	#0
		ADD		R0,	R0,	#10		; Print a new line
		OUT
		BRnzp	REDO		
EXIT	HALT					; Halt machine


;
; A subroutine to AND the values from R2 and R3 (R2 AND R3). The result is saved at R3.
;
MyAND	AND		R3, R2, R3	
		RET

;
; A subroutine to OR the values from R2 and R3 (R2 OR R3). The result is saved at R3.
;		
MyOR	NOT R2, R2
		NOT R3, R3
		AND R3, R2, R3
		NOT R3, R3
		RET

;
; A subroutine to add the values from R2 and R3 (R2 + R3). The result is saved at R3.
;	
MyADD	ADD R3, R2, R3
		RET

;
; A subroutine to subtract the value of R3 from R2 (R2 - R3). The result is saved at R3.
;
MySUB	NOT R3, R3
		ADD R3, R3, #1
		ADD R3, R2, R3
		RET

;
; A subroutine to multiply the value from R3 and R2 (R2 * R3). The result is saved at R3.
;
MyMULT	AND R1, R1, #0
		ADD R2, R2, #0

LOOP1	BRnz 	EXIT1
		ADD R1, R1, R3
		ADD R2, R2, #-1 	
		BRnzp 	LOOP1

EXIT1	ADD R3, R1, #0
		RET

;
; A subroutine to calculate the value from R2 to the power of the value from R3 (R2 ^ R3). The result is saved at R3.
;
MyPOW	LD  R0, VAL1
		STR R2, R0, #0
		STR R3, R0, #1
		LD  R3, ONE

LOOP2	LDR R1, R0, #1
		BRnz 	EXIT2
		LDR R2, R0, #0
		AND R1, R1, #0
		ADD R2, R2, #0

LOOP3	BRnz 	EXIT3
		ADD R1, R1, R3
		ADD R2, R2, #-1 	
		BRnzp 	LOOP3

EXIT3	ADD R3, R1, #0

		LDR R1, R0, #1
		ADD R1, R1, #-1
		STR R1, R0, #1
		BRnzp	LOOP2

EXIT2	RET

VAL1	.FILL	x5000
ONE		.FILL	#1

;
; A subroutine to output a 3-digit decimal result.
;
CONV	ADD		R1, R7, #0		; R3, R4, R5 and R7 are used in this subroutine
		JSR		Push
		ADD		R1, R3, #0		; R3 is the input value
		JSR		Push
		ADD		R1, R4, #0
		JSR		Push
		ADD		R1, R5, #0
		JSR		Push
		AND 	R5, R5, #0
OUT100	LD		R4, HUNDRED
		ADD		R4, R3, R4		; R3 - #100
		BRn		PRI100
		LD		R4, HUNDRED
		ADD		R3, R3, R4		; R3 - #100
		ADD		R5, R5, #1
		BRnzp	OUT100
PRI100	LD		R0, ASCII		; Load the ASCII template
		ADD		R0, R0, R5		; Convert binary count to ASCII
		OUT						; ASCII code in R0 is displayed.
		AND 	R5, R5, #0
OUT10	ADD		R4, R3, #-10
		BRn		PRI10
		ADD		R3, R3, #-10
		ADD		R5, R5, #1
		BRnzp	OUT10
PRI10	LD		R0, ASCII		; Load the ASCII template
		ADD		R0, R0, R5		; Convert binary count to ASCII
		OUT						; ASCII code in R0 is displayed.		
		LD		R0, ASCII
		ADD		R0, R0, R3		; Convert binary count to ASCII
		OUT						; ASCII code in R0 is displayed.
		JSR		Pop
		ADD		R5, R1, #0
		JSR		Pop
		ADD		R4, R1, #0
		JSR		Pop
		ADD		R3, R1, #0
		JSR		Pop
		ADD		R7, R1, #0
		RET

; Stack operations
Push	STR 	R1, R6, #0		; Stack Push
		ADD 	R6, R6, #-1 
		RET 
Pop 	ADD 	R6, R6, #1		; Stack Pop
		LDR 	R1, R6, #0
		RET
; End of the subroutine

PTR		.FILL	x3500
EMPTY 	.FILL 	x4000 
ASCII	.FILL	x0030			; '0'
ZERO	.FILL	xFFD0			; -'0'
HUNDRED	.FILL	xFF9C			; -#100
EQUAL	.FILL	x003D			; '='
PLUS	.FILL	x002B			; '+'
MINUS	.FILL	x002D			; '-'
MULT	.FILL	x002A			; '*'
SOR		.FILL	x007C			; '|'
SAND	.FILL	x0026			; '&'
POW		.FILL	x005E			; '^'
VAL		.BLKW	1
		.END


