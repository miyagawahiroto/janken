package window;

import javax.net.ssl.SSLEngineResult.Status;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;




import enums.Hands;

/**
 * ゲームのメイン画面
 */
public class MainWindow {

  /**
   * ゲームを表示するフレーム
   */
  private final JFrame frame;

  /**
   * メッセージを表示するラベル
   */
  private final JLabel messageLabel;

  /**
   * 画像を表示するラベル
   */
  private final JLabel gazouLabel;


  /**
   * アイコン作成
   */
  ImageIcon icon1 = new ImageIcon("img/takarakuji.png");
  
  ImageIcon icon2 = new ImageIcon("img/kuji_atari.png");
  
  ImageIcon icon3 = new ImageIcon("img/kuji_hazure.png");

  

  /**
   * グー　のボタン
   */
  private final JButton rockButton;

  /**
   * チョキ　のボタン
   */
  private final JButton scissorsButton;

  /**
   * パー　のボタン
   */
  private final JButton paperButton;

  /**
   * リセット　のボタン
   */
  private final JButton nextButton;

  /**
   * プレイ状況のステータス
   */
  private Status playState;

  /**
   * 相手が出した手
   */
  private Hands opponentHand;

  /**
   * コンストラクタ
   */
  public MainWindow() {
    // 画面生成
    this.frame = new JFrame("くじ引きゲーム！");
    this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // 画面サイズを指定
    this.frame.setBounds(200, 200, 600, 400);

    var pane = this.frame.getContentPane();

    // このcanvasに対して、ボタンやラベルを配置していく
    var canvas = new JPanel();
    // 自由レイアウトに変更する
    canvas.setLayout(null);

    //音楽再生


    // ラベル
    this.messageLabel = new JLabel("どれにしますか・・？");
    this.messageLabel.setBounds(20, 20, 400, 30);
    canvas.add(this.messageLabel);

    
    this.gazouLabel = new JLabel();
    this.gazouLabel.setBounds(250, 150, 100, 100);
    this.gazouLabel.setIcon(icon1);
    canvas.add(this.gazouLabel);

    // --------------
    // ボタンを作成する
    // --------------

    // グー
    this.rockButton = new JButton(Hands.Rock.getDisplay());
    this.rockButton.setBounds(100, 100, 100, 40);
    this.rockButton.addActionListener((e) -> this.selectHand(Hands.Rock));
    canvas.add(this.rockButton);

    // チョキ
    this.scissorsButton = new JButton(Hands.Scissors.getDisplay());
    scissorsButton.setBounds(250, 100, 100, 40);
    this.scissorsButton.addActionListener((e) -> this.selectHand(Hands.Scissors));
    canvas.add(scissorsButton);

    // パー
    this.paperButton = new JButton(Hands.Paper.getDisplay());
    paperButton.setBounds(400, 100, 100, 40);
    this.paperButton.addActionListener((e) -> this.selectHand(Hands.Paper));
    canvas.add(paperButton);

    // その他
    this.nextButton = new JButton("リセット");
    nextButton.setBounds(250, 250, 100, 40);
    this.nextButton.addActionListener((e) -> reset());
    canvas.add(nextButton);
    


    // 画面にCanvasを追加
    pane.add(canvas);
    
  }

  /**
   * 画面表示
   */
  public void show() {
    this.init();
    this.frame.setVisible(true);
  }

  /**
   * ゲームの初期化
   */
  public void init() {
    // 相手の手をリセットし、待ち状態にする
    this.opponentHand = Hands.getRandomHand();
    this.playState = Status.CLOSED;
  }
  public void reset() {
    // 相手の手をリセットし、待ち状態にする
    this.messageLabel.setText("リセット完了");
    this.opponentHand = Hands.getRandomHand();
    this.playState = Status.CLOSED;
    this.gazouLabel.setIcon(icon1);
  }

  /**
   * 自分の手を選んだ時の処理
   *
   * @param selected 選択した手
   */
  public void selectHand(Hands selected) {

    // 入力待ちでなければ以降の処理はしない
    if (this.playState != Status.CLOSED) {
      return;
    }

    // 勝ち負けの判定
    switch ((selected.getNumber() - opponentHand.getNumber() + 3) % 3) {
      case 0:
        // 引き分けなので継続
        this.messageLabel.setText("もう一回！！");
        // 手を出しなおす
        this.init();
        break;
      case 1:
        // 負け
        this.messageLabel.setText(String.format("ざんねん、はずれです"));
        this.gazouLabel.setIcon(icon3);
        // ゲーム終了
        this.playState = Status.OK;
        break;
      case 2:
        this.messageLabel.setText(String.format("おめでとう！あたりです！"));
        this.gazouLabel.setIcon(icon2);
        // ゲーム終了
        this.playState = Status.OK;
        break;
    }
  }

}