from AssCool_board  import *
# from AssCool_player import *


''' COMPSCI 130 (2020) - University of Auckland
    ASSIGNMENT ONE - Connect Four
    Simon Shan  441147157
'''

class Game:

    def __init__(self, num_players):
        self.players = [ Player(idx) 
                        for idx in range(num_players) ]
                                    # list of players objects
        self.size   = num_players*2+1
        self.board  = GameBoard(self.players, self.size)
        self.player = 0
        self.over   = False
        
    def loop(self):
        self.board.display()

        if not self.players[self.player].isAI:
            # get inputs from human players
            col = self.get_input()
        else:
            # make AI move
            c, maxPoints =self.board.column_resulting_in_max_points(1)
            if maxPoints>0:
                col=c
            else:
                # if no move adds new points choose move which minimises points opponent player gets
                (c,maxPoints)=self.board.column_resulting_in_max_points(2)
                if maxPoints>0:
                    col=c
                else:
                    # if no opponent move creates new points then choose column as close to middle as possible
                    col = self.board.free_slots_as_close_to_middle_as_possible()[0]


        self.board.add(col, self.player+1)

        self.over = self.board.game_over()
        self.next_player()

    def clean_up(self):
        self.board.display()

        # check who wins
        place = 0
        print('\n\n===== RESULTS =====')
        for p in sorted(self.players, key=self.get_points, reverse=True):
            place += 1
            print(f' {place}. {p.name:10}{p.points}')
        print()


    #==== helpers ====#
    def get_input(self):
        valid_slots = [str(col) for col in range(self.size)]
        prompt = f'{self.players[self.player].Name},\n - please choose a slot: '
        slot = input('\n'+prompt)

        # check valid input
        while True:
            if slot not in valid_slots:
                print(' - ** INVALID INPUT **')
            elif not self.board.num_free_positions_in_column(int(slot)):
                print(' - ** SLOT FULL **')
            else:
                break
            # re-prompt input
            slot = input(prompt)

        return int(slot)

    def next_player(self):
        self.player = (self.player+1) % len(self.players)

    def get_points(self, player):
            return player.points

