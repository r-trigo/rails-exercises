class TorneioJogador < ApplicationRecord
  has_and_belongs_to_many :torneio_equipamento
end
