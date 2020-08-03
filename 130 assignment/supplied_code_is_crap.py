from Ass import GameBoard


class FourInARow:
    def __init__(self, size):
        self.board=GameBoard(size)
    def play(self):
        print("*****************NEW GAME*****************")
        self.board.display()
        player_number=0
        print()
        while not self.board.game_over():
            print("Player ",player_number+1,": ")
            if player_number==0:
                while True:
                    try:
                        column = int(input("Please input slot: "))       
                    except ValueError:
                        print("Input must be an integer in the range 0 to ", self.board.size)
                        continue
                    else:
                        if column<0 or column>=self.board.size:
                            print("Input must be an integer in the range 0 to ", self.board.size)
                            continue
                        else:
                            if self.board.add(column, player_number+1):
                            # if True:
                                break
                            else:
                                print("Column ", column, "is alrady full. Please choose another one.")
                                continue
            else:
                # Choose move which maximises new points for computer player
                (c,maxPoints)=self.board.column_resulting_in_max_points(1)
                if maxPoints>0:
                    column=c
                else:
                    # if no move adds new points choose move which minimises points opponent player gets
                    (c,maxPoints)=self.board.column_resulting_in_max_points(2)
                    if maxPoints>0:
                        column=c
                    else:
                        # if no opponent move creates new points then choose column as close to middle as possible
                        column = self.board.free_slots_as_close_to_middle_as_possible()[0]
                self.board.add(column, player_number+1)
                print("The AI chooses column ", column)
            self.board.display()   
            player_number=(player_number+1)%2
        if (self.board.points[0]>self.board.points[1]):
            print("Player 1 (circles) wins!")
        elif (self.board.points[0]<self.board.points[1]):    
            print("Player 2 (crosses) wins!")
        else:  
            print("It's a draw!")
            
game = FourInARow(6)
game.play()        

