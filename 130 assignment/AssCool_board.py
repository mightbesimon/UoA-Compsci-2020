from AssCool_player import *


''' COMPSCI 130 (2020) - University of Auckland
    ASSIGNMENT ONE - Connect Four
    Simon Shan  441147157
'''

class GameBoard:

    def __init__(self, players, size):
        self.size = size
        self.players = players
        self.num_entries = [0] * size
        self.items = [[0] * size for i in range(size)]

    def num_free_positions_in_column(self, col):
        return self.size - self.num_entries[col]

    def game_over(self):
        return not any([
            self.num_free_positions_in_column(col)
            for col in range(self.size)
        ])                          # gameover if no free 
                                    # positions in any columns
    def display(self):
        # scoreboard
        scoreboard = (['#==== scoreboard ====#']
            + [ f' - Points {p.name}: {p.points}'
                                    for p in self.players ])
        '''     o
              x x
            x o x o
            o x o o x
        '''
        # disks
        for row in range(self.size)[::-1]:
            mask = [ self.players[disk-1].symbol
                    if disk else ' '
                    for disk in self.items[row] ]
                                    # mask the symbols onto .items
            print(f"{row}|{' '.join(mask)}|{row}", end='')
                                    # '*' operator unpacks mask for print()
            # print scoreboard
            try:
                print('\t\t'+scoreboard[self.size-row-1])
                                    # print the scoreboard
                                    # on the side of screen
            except:
                print()

        ''' ---------
            0 1 2 . n
        '''
        # line + col_number
        print(' +' + '-' * (self.size*2-1) + '+')
        print(' ', *range(self.size))    # '*' unpacks range() for printing

    def add(self, col, player):
        if not 0<=col<self.size: return False   # check if col is valid
                                                # return False if not

        row = self.num_entries[col]             # get avaliable row number
        if row >= self.size: return False       # check if column is full
                                                # return False if full

        self.items[row][col] = player           # update board
        self.num_entries[col] += 1              # update num disks in column
        self.players[player-1].points += self.num_new_points(
                                        row, col, player)
                                                # add points
        return True

    def valid_disk(self, row, col):
        ''' -> helper method <-
            for .is_a_point()
        '''
        if (0<=row<self.size                # verify if row and col indexes
                and 0<=col<self.size):      # are within the gameboard

            return self.items[row][col]     # return the disk if within
        else:
            raise IndexError                # raise IndexError otherwise

    def four_of_a_kind(self, coords, player):
        ''' -> helper method <-
            for .num_new_points()
        '''
        try:
            disks_of_4 = [ self.valid_disk(row, col) 
                                for row, col in coords ]
                                            # tries to take 4 connected disks
        except IndexError:
            return False        # might be out of bounds of the board
        else:
            return all([disk==player for disk in disks_of_4])
                                            # if 4-of-a-kind, return True

    def num_new_points(self, row, col, player):
        '''
        o     o     . | .     .     . | .     .     . | .     .     o
          o   o   .   |   o   o   .   |   .   .   o   |   .   .   o  
            o o .     |     o o o     |     o o o     |     . . o    
        o o o x . . . | . o o x o . . | . . o x o o . | . . . x o o o
            o . .     |     o o o     |     o o o     |     . o o    
          o   .   .   |   o   .   .   |   .   o   o   |   .   o   o  
        o     .     . | .     .     . | .     .     . | .     o     o
         offset = -3  |  offset = -2  |  offset = -1  |  offset = 0
        '''
        # check 4-in-a-row in these spots
        # 'o' are the spots the method will check for
        # 'x' is the spot where the new point is

        points = 0

        for offset in [-3, -2, -1, 0]:
            # horizontal
            coords = [ (row, col+offset+n)
                                for n in range(4) ]
                                        # coords of 4 connected disks
            if self.four_of_a_kind(coords, player): points += 1
                                        # +1 pts if is 4-of-a-kind
            # vertical
            coords = [ (row+offset+n, col)
                                for n in range(4) ]
            if self.four_of_a_kind(coords, player): points += 1

            # / diagonal
            coords = [ (row+offset+n, col+offset+n)
                                for n in range(4) ]
            if self.four_of_a_kind(coords, player): points += 1

            # \ diagonal
            coords = [ (row-offset-n, col+offset+n)
                                for n in range(4) ]
            if self.four_of_a_kind(coords, player): points += 1

        return points

    def distance_to_middle(self, col):
        ''' -> * helper method * <-
            return the distance to middle from a column
        '''
        return abs((self.size-1)/2 - col)   # this method is used as the key
                                    # for sorting the list of free columns

    def free_slots_as_close_to_middle_as_possible(self):
        free_slots = [ col for col in range(self.size)
            if self.num_free_positions_in_column(col) ]
                                            # list of col with free slots
        return sorted(free_slots,
            key=self.distance_to_middle)    # sort free_slots by
                                            # distance_to_middle

    def column_resulting_in_max_points(self, player):
        max_pts, max_col = -1, 0

        # find max points and the column number
        for col in self.free_slots_as_close_to_middle_as_possible():
                                            # iterate through the free slots
            row = self.num_entries[col]
            self.items[row][col] = player   # set player disk to calculate 
                                            # potential points
            num_pts = self.num_new_points(row, col, player)
                                            # potential points
            if max_pts < num_pts:
                max_pts, max_col = num_pts, col
                                            # update new max
            self.items[row][col] = 0        # revert back the disk

        return (max_col, max_pts)

