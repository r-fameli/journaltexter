import torch.nn as nn
import torch

# https://www.kaggle.com/arunmohan003/sentiment-analysis-using-lstm-pytorch


class SentimentRNN(nn.Module):
    def __init__(self, num_layers, vocab_size, hidden_dim, embedding_dim, output_dim=1, drop_prob=0.5):
        super(SentimentRNN, self).__init__()

        self.output_dim = output_dim
        self.hidden_dim = hidden_dim

        self.num_layers = num_layers
        self.vocab_size = vocab_size

        # embedding and LSTM layers
        self.embedding = nn.Embedding(vocab_size, embedding_dim)

        # lstm
        self.lstm = nn.LSTM(input_size=embedding_dim, hidden_size=self.hidden_dim,
                            num_layers=num_layers, batch_first=True)

        # dropout layer
        self.dropout = nn.Dropout(0)

        # linear and sigmoid layer
        self.fc = nn.Linear(self.hidden_dim, output_dim)
        self.sig = nn.Sigmoid()

    def forward(self, x, hidden):
        batch_size = x.size(0)
        # embeddings and lstm_out
        # shape: B x S x Feature   since batch = True
        embeds = self.embedding(x)
        # print(embeds.shape)  #[50, 500, 1000]
        lstm_out, hidden = self.lstm(embeds, hidden)

        lstm_out = lstm_out.contiguous().view(-1, self.hidden_dim)

        # dropout and fully connected layer
        # without dropout all predictions are same for some reason
        out = self.dropout(lstm_out)
        out = self.fc(out)

        # sigmoid function
        sig_out = self.sig(out)

        # reshape to be batch_size first
        sig_out = sig_out.view(batch_size, -1)

        sig_out = sig_out[:, -1]  # get last batch of labels

        # return last sigmoid output and hidden state
        return sig_out, hidden

    def init_hidden(self, batch_size):
        is_cuda = torch.cuda.is_available()

        if is_cuda:
            device = torch.device("cuda")
            print("GPU is available")
        else:
            device = torch.device("cpu")
            print("GPU not available, CPU used")

        h0 = torch.zeros((self.num_layers, batch_size,
                         self.hidden_dim)).to(device)
        c0 = torch.zeros((self.num_layers, batch_size,
                         self.hidden_dim)).to(device)
        hidden = (h0, c0)
        return hidden
