''' COMPSCI 130 (2020) - University of Auckland
    ASSIGNMENT ONE - Connect Four
    Simon Shan  441147157
'''

class Player:

    def __init__(self, idx, name=''):
        self.idx    = idx
        self.name   = name if name else f'player {idx+1}'
        self.Name   = f'PLAYER {name}' if name else f'PLAYER <-{idx+1}->'
        self.symbol = ['o', 'x', '-', '#'][idx]
        self.points = 0
        self.isAI   = False
